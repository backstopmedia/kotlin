package com.backstopmedia.kotlin.ktwitter.interactors

import android.util.Log
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApi
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.entities.Profile
import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.backstopmedia.kotlin.ktwitter.utils.functional.toMultimapBy
import com.twitter.sdk.android.core.TwitterSession
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Aaron Sarazan on 11/3/15
 * Copyright(c) 2015 Level, Inc.
 */

interface TopUsersInteractor {
    fun getFavoriteUsers(userId: Long): Observable<List<RankedUser>>
    fun getRetweetedUsers(userId: Long): Observable<List<RankedUser>>
    fun getTopUsers(userId: Long, filterFriends: Boolean = true): Observable<List<RankedUser>>
}

class TopUsersInteractorImpl(val session: TwitterSession) : TopUsersInteractor {

    private val kTwitterApi: KTwitterApi = KTwitterApiClient(session).kTwitterApi

    override fun getFavoriteUsers(userId: Long): Observable<List<RankedUser>> {
        return kTwitterApi.faves(user = userId, count = 1000).map {
            val userMap = it.map { it.user }.toMapBy { it.id }
            it.toMultimapBy { it.user.id }
                    .map { RankedUser.fromFaves(userMap[it.key]!!, it.value) }
                    .sortedByDescending { it.rank }
        }
    }

    override fun getRetweetedUsers(userId: Long): Observable<List<RankedUser>> {
        return kTwitterApi.userTimeline(user = userId, count = 1000).map {
            it.map { it.retweetedStatus }.filterNotNull()
        }.map {
            val userMap = it.map { it.user }.toMapBy { it.id }
            it.toMultimapBy { it.user.id }
                    .map { RankedUser.fromRetweets(userMap[it.key]!!, it.value) }
                    .sortedByDescending { it.rank }
        }
    }

    override fun getTopUsers(userId: Long, filterFriends: Boolean): Observable<List<RankedUser>> {
        return getFavoriteUsers(userId)
                .zipWith(getRetweetedUsers(userId)) {
                    it1, it2 ->
                    (it1 + it2).let {
                        val userMap = it.map { it.user }.toMapBy { it.id }
                        it.toMultimapBy { it.user.id }.map {
                            RankedUser(userMap[it.key]!!, it.value.flatMap { it.tweets }, it.value.sumBy { it.rank })
                        }.sortedByDescending { it.rank }
                    }
                }
                .let {
                    if (filterFriends) {
                        it.zipWith(kTwitterApi.following(userId)) {
                            users, following ->
                            following.ids.toSet().let {
                                ids ->
                                users.filter { it.user.id !in ids }
                            }
                        }
                    } else { it }
                }
    }
}