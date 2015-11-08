package com.backstopmedia.kotlin.ktwitter.utils.os

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import org.jetbrains.anko.intentFor
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegated Property that will grab its value from the activity's intent.
 *
 * @sample
 * val uid: Long? by intentExtra("uid")
 */
inline fun <reified T : Any> intentExtra(key: String): ReadOnlyProperty<Activity, T?> {
    return IntentProperty(key, T::class.java)
}

class IntentProperty<T : Any>(val key: String, val cls: Class<T>) : ReadOnlyProperty<Activity, T?> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): T? {
        return thisRef.getExtra(key, cls)
    }
}

inline fun <reified T : Any> Activity.getExtra(key: String): T? {
    return intent.getExtra(key, T::class.java)
}

fun <T : Any> Activity.getExtra(key: String, cls: Class<T>): T? {
    return intent.getExtra(key, cls)
}

inline fun <reified T : Any> Intent?.getExtra(key: String): T? {
    return getExtra(key, T::class.java)
}

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_UNIT_OR_ANY")
fun <T : Any> Intent?.getExtra(key: String, cls: Class<T>): T? {
    if (this == null || !hasExtra(key)) return null
    return when {
        cls == String::class -> getStringExtra(key)
        cls == Int::class -> getIntExtra(key, 0)
        cls == Long::class -> getLongExtra(key, 0L)
        Serializable::class.java.isAssignableFrom(cls) -> getSerializableExtra(key)
        Parcelable::class.java.isAssignableFrom(cls) -> getParcelableExtra<Parcelable>(key)
        // You get the picture. Add more if you need them!
        else -> throw IllegalArgumentException("Cannot get $cls from Intent")
    } as T?    
}