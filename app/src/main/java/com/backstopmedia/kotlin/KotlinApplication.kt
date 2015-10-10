package com.backstopmedia.kotlin

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.backstopmedia.kotlin.twitter.MyTwitter
import com.backstopmedia.kotlin.twitter.kallback
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Search
import io.fabric.sdk.android.Fabric

class KotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Twitter(TwitterAuthConfig(MyTwitter.KEY, MyTwitter.SECRET)))

        // Test run (or twitter the hard way!)
        // We should be able to hack away a lot of this boilerplate.
        Twitter.getInstance().core.logInGuest(kallback {
            Twitter.getApiClient().searchService.tweets("Kotlin", null, null, null, null, null, null, null, null, null,
                    kallback {
                        Toast.makeText(this,
                                "Fetched ${it.data.tweets.size()} tweets", Toast.LENGTH_LONG)
                                .show()
                    }
            )
        })
    }
}