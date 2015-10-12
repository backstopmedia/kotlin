package com.backstopmedia.kotlin.chapter3.api

import android.util.Log
import com.backstopmedia.kotlin.chapter3.twitter.kallback
import com.backstopmedia.kotlin.chapter3.twitter.tweets
import com.twitter.sdk.android.Twitter

/**
 * Created by Aaron Sarazan on 10/11/15
 * Copyright(c) 2015 Level, Inc.
 */
object ApiDemoKotlin {

    fun basicSearch() {
        Twitter.getInstance().core.logInGuest(kallback {
            Twitter.getApiClient().searchService.tweets("Kotlin", callback = kallback {
                Log.i("ApiDemoKotlin", "Found ${it.data.tweets.size()} tweets.")
            })
        })
    }
}