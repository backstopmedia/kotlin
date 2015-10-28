package com.backstopmedia.kotlin.ktwitter.interactors

import com.backstopmedia.kotlin.ktwitter.api.KTwitterApi
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.entities.Profile
import com.twitter.sdk.android.core.TwitterSession
import rx.Observable

interface UserInteractor {

    fun getCurrentUser(): Observable<Profile>
}

class UserInteractorImpl(val session: TwitterSession) : UserInteractor {

    private val kTwitterApi: KTwitterApi = KTwitterApiClient(session).kTwitterApi

    override fun getCurrentUser(): Observable<Profile> =
            kTwitterApi.getUser(screenName = session.userName)
                    .map { Profile.fromUser(it) }

}