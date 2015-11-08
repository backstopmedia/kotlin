package com.backstopmedia.kotlin.ktwitter.interactors

import com.backstopmedia.kotlin.ktwitter.api.KTwitterApi
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.api.getFollowingIds
import com.backstopmedia.kotlin.ktwitter.api.getRetweets
import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.backstopmedia.kotlin.ktwitter.utils.functional.toMultimapBy
import com.backstopmedia.kotlin.ktwitter.utils.twitter.tweetsByUser
import com.twitter.sdk.android.core.TwitterSession
import rx.Observable

interface TopUsersInteractor {
    fun getTopUsers(userId: Long): Observable<List<RankedUser>>
}

class TopUsersInteractorImpl(val session: TwitterSession) : TopUsersInteractor {

    private val kTwitterApi: KTwitterApi = KTwitterApiClient(session).kTwitterApi

    /**
     * Takes [getFavoriteUsers] and [getRetweetedUsers]
     * and applies Rx's [zipWith] function to merge the results.
     *
     * Ranking algorithm:
     * 2 pts/retweet
     * 1 pts/fave
     */
    override fun getTopUsers(userId: Long): Observable<List<RankedUser>> {
        return getFavoriteUsers(userId).zipWith(getRetweetedUsers(userId)) {
            faves, retweets ->
            (faves + retweets).let {
                it.toMultimapBy { it.user }.map {
                    it.value.first() + it.value.lastOrNull()
                }.sortedByDescending { it.rank }
            }
        }
    }

    /**
     * Get a list of the most faved users in your last [count] tweets
     * Also resolves following status via a separate [kTwitterApi.getFollowing] call
     */
    fun getFavoriteUsers(userId: Long): Observable<List<RankedUser>> = with(kTwitterApi) {
        getFaves(userId).zipWith(getFollowingIds(session.userId)) {
            tweets, following ->
            val userMap = tweets.map { it.user }.toMapBy { it.id }
            tweets.tweetsByUser().map {
                RankedUser.fromFaves(userMap[it.key]!!, it.value, following)
            }.sortedByDescending { it.rank }
        }
    }

    /**
     * Get a list of the most RT'd users in your last [count] tweets
     * Also resolves following status via a separate [kTwitterApi.getFollowing] call
     */
    fun getRetweetedUsers(userId: Long): Observable<List<RankedUser>> = with(kTwitterApi) {
        getRetweets(userId).zipWith(getFollowingIds(session.userId)) {
            tweets, following ->
            val userMap = tweets.map { it.user }.toMapBy { it.id }
            tweets.tweetsByUser().map {
                RankedUser.fromRetweets(userMap[it.key]!!, it.value, following)
            }.sortedByDescending { it.rank }
        }
    }
}