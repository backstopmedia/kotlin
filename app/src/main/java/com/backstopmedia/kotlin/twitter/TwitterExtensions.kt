package com.backstopmedia.kotlin.twitter

import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.services.SearchService
import com.twitter.sdk.android.core.services.params.Geocode
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import com.twitter.sdk.android.tweetui.TweetUi

/**
 * Accessor to Kit for authenticated requests to Twitter API
 */
val twitterCore: TwitterCore
    get() = Twitter.getInstance().core

/**
 * Accessor to Kit for composing tweets
 */
val tweetComposer: TweetComposer
    get() = Twitter.getInstance().tweetComposer

/**
 * Accessor to Kit for rendering tweets
 */
val tweetUi: TweetUi
    get() = Twitter.getInstance().tweetUi

/**
 * Accessor to Twitter Api Client
 */
val twitterApiClient: TwitterApiClient
    get() = Twitter.getApiClient()

/**
 * Extension function for tweets method on [SearchService]
 *
 * Provides default values for all params except [callback]
 */
fun SearchService.tweets(query: String? = null,
                         geocode: Geocode? = null,
                         lang: String? = null,
                         locale: String? = null,
                         resultType: String? = null,
                         count: Int? = null,
                         until: String? = null,
                         sinceId: Long? = null,
                         maxId: Long? = null,
                         includeEntities: Boolean? = null,
                         callback: Callback<Search>)
{
    return tweets(query, geocode, lang, locale, resultType, count, until, sinceId, maxId, includeEntities, callback)
}
