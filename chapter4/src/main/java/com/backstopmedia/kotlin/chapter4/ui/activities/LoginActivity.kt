package com.backstopmedia.kotlin.chapter4.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.backstopmedia.kotlin.chapter4.R
import com.backstopmedia.kotlin.chapter4.utils.kallback
import kotlinx.android.synthetic.activity_login.login

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