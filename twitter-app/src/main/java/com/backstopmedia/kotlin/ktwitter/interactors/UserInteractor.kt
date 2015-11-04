package com.backstopmedia.kotlin.ktwitter.interactors

import android.util.Log
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApi
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.entities.Profile
import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.backstopmedia.kotlin.ktwitter.utils.functional.sortKeysByValue
import com.backstopmedia.kotlin.ktwitter.utils.functional.toMultimapBy
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.User
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