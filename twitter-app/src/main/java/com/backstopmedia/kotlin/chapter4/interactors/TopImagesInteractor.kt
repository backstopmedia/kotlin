package com.backstopmedia.kotlin.chapter4.interactors

import com.backstopmedia.kotlin.chapter4.api.KTwitterApi
import com.backstopmedia.kotlin.chapter4.api.KTwitterApiClient
import com.backstopmedia.kotlin.chapter4.entities.Image
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Tweet
import rx.Observable

/**
 * Created by Tudor Luca on 14/10/15.
 */
interface TopImagesInteractor {

    fun getMostRecentImages(): Observable<List<Image>>
}

class TopImagesInteractorImpl(val session: TwitterSession) : TopImagesInteractor {

    private val kTwitterApi: KTwitterApi

    init {
        kTwitterApi = KTwitterApiClient(session).kTwitterApi
    }

    override fun getMostRecentImages(): Observable<List<Image>> =
            kTwitterApi.homeTimeline(count = 200)
                    .map {
                        it.filter { it.hasImage() }.map { Image.fromTweet(it) }.sortedByDescending { it.retweetCount }
                    }
}

fun Tweet.hasImage(): Boolean =
        if (entities == null || entities.media == null || entities.media.isEmpty()) false
        else with(entities.media) {
            val firstEntry = first()
            if (firstEntry == null || !firstEntry.type.equals("photo")) false
            else true
        }

