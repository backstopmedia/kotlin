package com.backstopmedia.kotlin.ktwitter.interactors

import com.backstopmedia.kotlin.ktwitter.api.KTwitterApi
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.entities.FollowingUser
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.User
import rx.Observable

interface FollowersInteractor {
    fun getFollowers(userId: Long): Observable<List<FollowingUser>>
}

class FollowersInteractorImpl(val session: TwitterSession) : FollowersInteractor {

    private val kTwitterApi: KTwitterApi = KTwitterApiClient(session).kTwitterApi

    override fun getFollowers(userId: Long): Observable<List<FollowingUser>> {
        return kTwitterApi.getFollowers(userId, count = 200).map {
            it.users.sortedBy { it.name }
        }.zipWith(kTwitterApi.following(userId)) { followers, following ->
            followers.map {
                FollowingUser(it, it.id in following.ids)
            }
        }
    }
}