package com.backstopmedia.kotlin.chapter4.api

import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

class KTwitterApiClient(val session: TwitterSession) : TwitterApiClient(session) {

    val kTwitterApi: KTwitterApi by lazy { getService(KTwitterApi::class.java) }
}

interface KTwitterApi {

    @GET("/1.1/statuses/home_timeline.json")
    fun homeTimeline(@Query("count") count: Int = 20,
                     @Query("trim_user") trimUser: Boolean = true,
                     @Query("exclude_replies") excludeReplies: Boolean = true,
                     @Query("contributor_details") contributorDetails: Boolean = true,
                     @Query("include_entities") includeEntities: Boolean = true): Observable<List<Tweet>>

    @GET("/1.1/users/show.json")
    fun getUser(@Query("screen_name") screenName: String, @Query("include_entities") includeEntities: Boolean = true): Observable<User>
}