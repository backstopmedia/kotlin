package com.backstopmedia.kotlin.chapter4

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Tudor Luca on 14/10/15.
 */
class TopImagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationHelper.setup(this, R.layout.activity_top_images)
    }
}