package com.backstopmedia.kotlin.twitter.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import butterknife.bindView
import com.backstopmedia.kotlin.R
import com.backstopmedia.kotlin.twitter.kallback
import com.twitter.sdk.android.core.identity.TwitterLoginButton


/**
 * Created by Aaron Sarazan on 10/11/15.
 * Copyright(c) 2015 Level, Inc.
 */
class LoginActivity : AppCompatActivity() {

    val loginButton: TwitterLoginButton by bindView(R.id.login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.callback = kallback {
            Toast.makeText(this, "Logged in as ${it.data.userName}", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LandingActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton.onActivityResult(requestCode, resultCode, data)
    }
}