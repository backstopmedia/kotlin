package com.backstopmedia.kotlin.chapter4.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.twitter.sdk.android.core.TwitterCore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (TwitterCore.getInstance()?.sessionManager?.activeSession) {
            null -> goToLogin()
            else -> goToTimeline()
        }
    }

    private fun goToLogin() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToTimeline() {
        finish()
        startActivity(Intent(this, TimelineActivity::class.java))
    }
}