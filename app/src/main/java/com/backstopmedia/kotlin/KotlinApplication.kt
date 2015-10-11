package com.backstopmedia.kotlin

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.backstopmedia.kotlin.twitter.MyTwitter
import com.backstopmedia.kotlin.twitter.kallback
import com.backstopmedia.kotlin.twitter.tweets
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import io.fabric.sdk.android.Fabric

class KotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Twitter(TwitterAuthConfig(MyTwitter.KEY, MyTwitter.SECRET)))

        // All hail kallbacks.
//        Twitter.getInstance().core.logInGuest(kallback {
//            Twitter.getApiClient().searchService.tweets("Kotlin", callback = kallback {
//                Toast.makeText(this, "Found ${it.data.tweets.size()} tweets.", Toast.LENGTH_LONG).show()
//            })
//        })
    }
}