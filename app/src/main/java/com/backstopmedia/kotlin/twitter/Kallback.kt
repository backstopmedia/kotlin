package com.backstopmedia.kotlin.twitter

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException

fun <T: Any> kallback(onSuccess: (Result<T>) -> Unit = {}): Kallback<T> {
    return Kallback(onSuccess)
}

class Kallback<T: Any>(success: (Result<T>) -> Unit) : Callback<T>() {

    private val onSuccess: (Result<T>) -> Unit = success
    private var onFail: (TwitterException) -> Unit = {}

    fun onFail(fn: (TwitterException) -> Unit): Kallback<T> {
        onFail = fn
        return this
    }

    override fun success(result: Result<T>) {
        onSuccess.invoke(result)
    }

    override fun failure(exception: TwitterException) {
        onFail.invoke(exception)
    }
}