package com.backstopmedia.kotlin

import android.app.Application
import android.util.Log
import com.backstopmedia.kotlin.twitter.MyTwitter
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.services.SearchService
import com.twitter.sdk.android.core.services.params.Geocode
import io.fabric.sdk.android.Fabric


/**
 * Created by Aaron Sarazan on 10/10/15.
 * Copyright(c) 2015 Level, Inc.
 */
class KotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Twitter(TwitterAuthConfig(MyTwitter.KEY, MyTwitter.SECRET)))

        // Test run (or twitter the hard way!)
        // We should be able to hack away a lot of this boilerplate.
        Twitter.getInstance().core.logInGuest(object : Callback<AppSession>() {
            override fun failure(p0: TwitterException?) {
                throw UnsupportedOperationException()
            }

            override fun success(p0: Result<AppSession>?) {
                Twitter.getApiClient().searchService.tweets("Kotlin", callback = object : Callback<Search>() {
                    override fun failure(p0: TwitterException?) {
                        throw UnsupportedOperationException()
                    }

                    override fun success(p0: Result<Search>?) {
                        Log.d("Kotlin", "Found ${p0?.data?.tweets?.size() ?: 0} tweets")
                    }
                })
            }
        })
    }
}

fun SearchService.tweets(s: String, geocode: Geocode? = null, s1: String? = null, s2: String? = null, s3: String? = null,
                         integer: Int? = null, s4: String? = null, aLong: Long? = null, aLong1: Long? = null,
                         aBoolean: Boolean? = null, callback: Callback<Search>) =
        tweets(s, geocode, s1, s2, s3, integer, s4, aLong, aLong1, aBoolean, callback)