package com.backstopmedia.kotlin

import android.app.Application
import android.util.Log
import com.backstopmedia.kotlin.twitter.MyTwitter
import com.backstopmedia.kotlin.twitter.tweets
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Search
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