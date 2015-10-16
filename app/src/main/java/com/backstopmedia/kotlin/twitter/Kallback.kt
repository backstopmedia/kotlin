package com.backstopmedia.kotlin.twitter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.backstopmedia.kotlin.util.ui.toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException

/**
 * A convenience method for generating callbacks.
 *
 * @param onSuccess block to call on successful return
 * @return A [Callback] object with basic error logging.
 */
fun <T: Any> kallback(onSuccess: (Result<T>) -> Unit = {}): Kallback<T> {
    return Kallback(onSuccess).onFail {
        Log.w("kallback", "Something went wrong: $it")
    }
}

/**
 * Specialization of [kallback]
 *
 * This version, since it has access to the calling Context,
 * is able to pop up a toast on error states.
 *
 * @param onSuccess block to call on successful return
 * @return A [Callback] object with basic error logging.
 */
fun <T: Any> Context.kallback(onSuccess: (Result<T>) -> Unit = {}): Kallback<T> {
    return Kallback(onSuccess).onFail {
        Log.w("kallback", "Something went wrong: $it")
        toast("Something went wrong: ${it.getMessage()}")
    }
}

/**
 * A streamlined subclass of [Callback] with optional failure behavior.
 *
 * @param T the response type, passed up to [Callback]
 * @param onSuccess block to call on successful return
 * @see onFail for optional fail block.
 */
open class Kallback<T: Any>(private val onSuccess: (Result<T>) -> Unit) : Callback<T>() {

    private var onFail: ((TwitterException) -> Unit)? = null

    /**
     * Sets failure behavior. Can be chained from [kallback] or constructor.
     *
     * @param onFail block to run on failed request.
     */
    fun onFail(onFail: (TwitterException) -> Unit): Kallback<T> {
        this.onFail = onFail
        return this
    }

    override fun success(result: Result<T>) {
        onSuccess.invoke(result)
    }

    override fun failure(exception: TwitterException) {
        onFail?.invoke(exception)
    }
}