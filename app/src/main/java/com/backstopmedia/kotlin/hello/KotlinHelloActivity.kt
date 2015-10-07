package com.backstopmedia.kotlin.hello

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.backstopmedia.kotlin.R

class KotlinHelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener {
            button.text = "Hello World!"
        }
    }
}
