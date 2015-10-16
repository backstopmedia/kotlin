package com.backstopmedia.kotlin.util.ui

import android.content.Context
import android.widget.Toast

/**
 * Created by Aaron Sarazan on 10/15/15
 * Copyright(c) 2015 Level, Inc.
 */

public fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG): Toast {
    val retval = Toast.makeText(this, message, duration)
    retval.show()
    return retval
}