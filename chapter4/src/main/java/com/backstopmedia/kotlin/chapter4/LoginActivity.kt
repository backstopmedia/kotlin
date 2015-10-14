package com.backstopmedia.kotlin.chapter4

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.activity_login.login

/**
 * Created by Aaron Sarazan on 10/11/15.
 * Copyright(c) 2015 Level, Inc.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.callback = kallback {
            goToTimeline()
            Toast.makeText(this, "Logged in as ${it.data.userName}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        login.onActivityResult(requestCode, resultCode, data)
    }

    private fun goToTimeline() {
        finish()
        startActivity(Intent(this, TimelineActivity::class.java))
    }
}