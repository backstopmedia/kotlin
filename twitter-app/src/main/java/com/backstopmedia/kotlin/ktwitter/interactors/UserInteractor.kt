package com.backstopmedia.kotlin.ktwitter.interactors

import com.backstopmedia.kotlin.ktwitter.api.KTwitterApi
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.entities.Profile
import com.backstopmedia.kotlin.ktwitter.utils.functional.sortKeysByValue
import com.backstopmedia.kotlin.ktwitter.utils.functional.toMultimapBy
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.User
import rx.Observable

interface UserInteractor {
    fun getCurrentUser(): Observable<Profile>

    /**
     * Fetch [userId]'s favorite users, based on their most recently-faved tweets.
     * @return A sorted list of pairs consisting of a user and their number of faved tweets by [userId]
     */
    fun getFavoriteUsers(userId: Long, pageSize: Int = 20, cursor: Int = 0): Observable<List<Pair<User, Int>>>
}

class UserInteractorImpl(val session: TwitterSession) : UserInteractor {

    private val kTwitterApi: KTwitterApi = KTwitterApiClient(session).kTwitterApi

    override fun getCurrentUser(): Observable<Profile> =
            kTwitterApi.getUser(screenName = session.userName)
                    .map { Profile.fromUser(it) }

    override fun getFavoriteUsers(userId: Long, pageSize: Int, cursor: Int): Observable<List<Pair<User, Int>>> {
        return kTwitterApi.faves(user = userId, count = 500).map {
            val userMap = it.map { it.user }.toMapBy { it.id }      // we'll need this to turn our ids back into Users
            it.toMultimapBy { it.user.id }                          // List<Long, List<Tweet> (User doesn't hash, so can't be a key)
                    .map { Pair(userMap[it.key]!!, it.value.size) }
                    .sortedByDescending { it.second }
                    .drop(cursor)
                    .take(pageSize)
        }
    }
}