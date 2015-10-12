package com.backstopmedia.kotlin.chapter3.api

import android.util.Log
import com.backstopmedia.kotlin.chapter3.twitter.kallback
import com.backstopmedia.kotlin.chapter3.twitter.tweets
import com.twitter.sdk.android.Twitter

object ApiDemoKotlin {

    fun basicSearch() {
        Twitter.getInstance().core.logInGuest(kallback {
            Twitter.getApiClient().searchService.tweets("Kotlin", callback = kallback {
                Log.i("ApiDemoKotlin", "Found ${it.data.tweets.size()} tweets.")
            })
        })
    }
}