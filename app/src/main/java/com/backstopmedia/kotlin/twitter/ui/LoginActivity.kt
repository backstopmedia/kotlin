package com.backstopmedia.kotlin.twitter.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.backstopmedia.kotlin.R
import com.backstopmedia.kotlin.twitter.kallback
import com.backstopmedia.kotlin.util.ui.startActivity
import com.backstopmedia.kotlin.util.ui.toast
import kotlinx.android.synthetic.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.callback = kallback {
            toast("Logged in as ${it.data.userName}")
            startActivity(LandingActivity::class.java)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        login.onActivityResult(requestCode, resultCode, data)
    }
}