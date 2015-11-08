package com.backstopmedia.kotlin.ktwitter.interactors

import com.backstopmedia.kotlin.ktwitter.api.KTwitterApi
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.backstopmedia.kotlin.ktwitter.utils.functional.toMultimapBy
import com.twitter.sdk.android.core.TwitterSession
import rx.Observable

interface TopUsersInteractor {
    fun getFavoriteUsers(userId: Long): Observable<List<RankedUser>>
    fun getRetweetedUsers(userId: Long): Observable<List<RankedUser>>
    fun getTopUsers(userId: Long, filterFriends: Boolean = true): Observable<List<RankedUser>>
}

class TopUsersInteractorImpl(val session: TwitterSession) : TopUsersInteractor {

    private val kTwitterApi: KTwitterApi = KTwitterApiClient(session).kTwitterApi

    /**
     * Get a list of the most faved users in your last [count] tweets
     * Also resolves following status via a separate [kTwitterApi.getFollowing] call
     */
    override fun getFavoriteUsers(userId: Long): Observable<List<RankedUser>> {
        return kTwitterApi.getFaves(user = userId, count = 1000)
                .zipWith(kTwitterApi.getFollowing(session.userId).map { it.ids.toSet() }) {
                    tweets, following ->
                    val userMap = tweets.map { it.user }.toMapBy { it.id }
                    tweets.toMultimapBy { it.user.id }
                            .map { RankedUser.fromFaves(userMap[it.key]!!, it.value, following) }
                            .sortedByDescending { it.rank }
                }
    }

    /**
     * Get a list of the most RT'd users in your last [count] tweets
     * Also resolves following status via a separate [kTwitterApi.getFollowing] call
     */
    override fun getRetweetedUsers(userId: Long): Observable<List<RankedUser>> {
        return kTwitterApi.userTimeline(user = userId, count = 1000).map {
            it.map { it.retweetedStatus }.filterNotNull()
        }.zipWith(kTwitterApi.getFollowing(session.userId).map { it.ids.toSet() }) {
            tweets, following ->
            val userMap = tweets.map { it.user }.toMapBy { it.id }
            tweets.toMultimapBy { it.user.id }
                    .map { RankedUser.fromRetweets(userMap[it.key]!!, it.value, following) }
                    .sortedByDescending { it.rank }
        }
    }

    /**
     * Takes [getFavoriteUsers] and [getRetweetedUsers]
     * and applies Rx's [zipWith] function to merge the results.
     *
     * Ranking algorithm:
     * 2 pts/retweet
     * 1 pts/fave
     */
    override fun getTopUsers(userId: Long, filterFriends: Boolean): Observable<List<RankedUser>> {
        return getFavoriteUsers(userId)
                .zipWith(getRetweetedUsers(userId)) {
                    faves, retweets ->
                    (faves + retweets).let {
                        val userMap = it.map { it.user }.toMapBy { it.id }
                        it.toMultimapBy { it.user.id }.map {
                            RankedUser(userMap[it.key]!!, it.value.flatMap { it.tweets }, it.value.sumBy { it.rank }, it.value.any { it.following })
                        }.sortedByDescending { it.rank }
                    }
                }
                .let {
                    if (filterFriends) {
                        it.zipWith(kTwitterApi.getFollowing(userId)) {
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