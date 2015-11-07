package com.backstopmedia.kotlin.ktwitter.utils.os

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import org.jetbrains.anko.intentFor
import java.io.Serializable

/**
 * Created by Aaron Sarazan on 11/4/15
 * Copyright(c) 2015 Level, Inc.
 */

inline fun <reified T : Any> Activity.getExtra(key: String): T? {
    return intent.getExtra(key)
}

@Suppress("IMPLICIT_CAST_TO_UNIT_OR_ANY")
inline fun <reified T : Any> Intent?.getExtra(key: String): T? {val cls = T::class.java
    if (this == null || !hasExtra(key)) return null
    return when {
        cls == String::class -> getStringExtra(key)
        cls == Int::class -> getIntExtra(key, 0)
        cls == Long::class -> getLongExtra(key, 0L)
        Serializable::class.java.isAssignableFrom(cls) -> getSerializableExtra(key)
        Parcelable::class.java.isAssignableFrom(cls) -> getParcelableExtra<Parcelable>(key)
        // You get the picture. Add more if you need them!
        else -> throw IllegalArgumentException("Cannot get ${T::class.java} from Intent")
    } as T?    
}