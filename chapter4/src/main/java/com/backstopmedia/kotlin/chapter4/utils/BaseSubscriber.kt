package com.backstopmedia.kotlin.chapter4.utils

import rx.Subscriber

/**
 * Created by Tudor Luca on 15/10/15.
 */
open class BaseSubscriber<T>() : Subscriber<T>() {

    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
    }

    override fun onNext(t: T) {
    }
}