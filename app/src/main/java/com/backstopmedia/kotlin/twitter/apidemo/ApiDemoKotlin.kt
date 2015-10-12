package com.backstopmedia.kotlin.twitter.apidemo

import android.util.Log
import android.widget.Toast
import com.backstopmedia.kotlin.twitter.kallback
import com.backstopmedia.kotlin.twitter.tweets
import com.backstopmedia.kotlin.twitter.twitterApiClient
import com.backstopmedia.kotlin.twitter.twitterCore
import com.twitter.sdk.android.Twitter

/**
 * Created by Aaron Sarazan on 10/11/15
 * Copyright(c) 2015 Level, Inc.
 */
object ApiDemoKotlin {

    fun basicSearch() {
        // All hail kallbacks.
        twitterCore.logInGuest(kallback {
            twitterApiClient.searchService.tweets("Kotlin", callback = kallback {
                Log.i("ApiDemoKotlin", "Found ${it.data.tweets.size()} tweets.")
            })
        })
    }
}