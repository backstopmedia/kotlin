package com.backstopmedia.kotlin.ktwitter.utils.os

import android.app.Activity
import android.os.Parcelable
import org.jetbrains.anko.intentFor
import java.io.Serializable

/**
 * Created by Aaron Sarazan on 11/4/15
 * Copyright(c) 2015 Level, Inc.
 */

@Suppress("IMPLICIT_CAST_TO_UNIT_OR_ANY")
inline fun <reified T : Any> Activity.getFromIntent(key: String): T? {
    if (intent == null) return null
    if (!intent.hasExtra(key)) return null
    val cls = T::class.java
    return when {
        cls == String::class -> intent.getStringExtra(key)
        cls == Int::class -> intent.getIntExtra(key, 0)
        cls == Long::class -> intent.getLongExtra(key, 0L)
        Serializable::class.java.isAssignableFrom(cls) -> intent.getSerializableExtra(key)
        Parcelable::class.java.isAssignableFrom(cls) -> intent.getParcelableExtra<Parcelable>(key)
        // You get the picture. Add more if you need them!
        else -> throw IllegalArgumentException("Cannot get ${T::class.java} from Intent")
    } as T?
}