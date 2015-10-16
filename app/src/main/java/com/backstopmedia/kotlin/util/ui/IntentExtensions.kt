package com.backstopmedia.kotlin.util.ui

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Created by Aaron Sarazan on 10/15/15
 * Copyright(c) 2015 Level, Inc.
 */

fun <T : Activity> Context.startActivity(cls: Class<T>): Intent {
    val intent = Intent(this, cls)
    startActivity(intent)
    return intent
}