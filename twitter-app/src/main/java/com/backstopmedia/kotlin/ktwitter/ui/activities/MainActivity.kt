package com.backstopmedia.kotlin.ktwitter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.twitter.sdk.android.core.TwitterCore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
        startActivity(redirectIntent())
    }

    private fun redirectIntent(): Intent {
        return when (TwitterCore.getInstance()?.sessionManager?.activeSession) {
            null -> Intent(this, LoginActivity::class.java)
            else -> Intent(this, TimelineActivity::class.java)
        }
    }
}