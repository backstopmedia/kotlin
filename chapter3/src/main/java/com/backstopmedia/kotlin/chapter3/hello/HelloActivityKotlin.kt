package com.backstopmedia.kotlin.chapter3.hello

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.backstopmedia.kotlin.chapter3.R
import kotlinx.android.synthetic.activity_hello.button
import kotlinx.android.synthetic.activity_hello.editText

class HelloActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        button.setOnClickListener {
            val name = editText.text.toString()
            button.text = "Hello $name!"
        }
    }
}
