package com.backstopmedia.kotlin.twitter.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.twitter.sdk.android.Twitter

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Twitter.getInstance().core.sessionManager.activeSession != null) {
            startActivity(Intent(this, TimelineActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}