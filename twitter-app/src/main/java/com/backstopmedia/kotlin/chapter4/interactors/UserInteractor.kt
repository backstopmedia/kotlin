package com.backstopmedia.kotlin.chapter4.interactors

import com.backstopmedia.kotlin.chapter4.api.KTwitterApi
import com.backstopmedia.kotlin.chapter4.api.KTwitterApiClient
import com.backstopmedia.kotlin.chapter4.entities.Profile
import com.twitter.sdk.android.core.TwitterSession
import rx.Observable

/**
 * Created by Tudor Luca on 14/10/15.
 */
interface UserInteractor {

    fun getCurrentUser(): Observable<Profile>
}

class UserInteractorImpl(val session: TwitterSession) : UserInteractor {

    private val kTwitterApi: KTwitterApi = KTwitterApiClient(session).kTwitterApi

    override fun getCurrentUser(): Observable<Profile> =
            kTwitterApi.getUser(screenName = session.userName)
                    .map { Profile.fromUser(it) }
}