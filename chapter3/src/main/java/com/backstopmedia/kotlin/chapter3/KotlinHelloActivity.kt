package com.backstopmedia.kotlin.chapter3

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class KotlinHelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val editText = findViewById(R.id.editText) as EditText

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener {
            val name = editText.text
            button.text = "Hello $name!"
        }
    }
}
