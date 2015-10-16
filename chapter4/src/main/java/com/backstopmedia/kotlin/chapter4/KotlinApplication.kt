package com.backstopmedia.kotlin.chapter4

import android.app.Application
import com.backstopmedia.kotlin.chapter4.utils.MyTwitter
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import io.fabric.sdk.android.Fabric

class KotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Twitter(TwitterAuthConfig(MyTwitter.KEY, MyTwitter.SECRET)))
    }
}