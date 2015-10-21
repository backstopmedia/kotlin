package com.backstopmedia.kotlin.ktwitter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.utils.kallback
import kotlinx.android.synthetic.activity_login.login
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.callback = kallback {
            finish()
            startActivity(Intent(this, TimelineActivity::class.java))
            toast("Logged in as ${it.data.userName}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        login.onActivityResult(requestCode, resultCode, data)
    }
}