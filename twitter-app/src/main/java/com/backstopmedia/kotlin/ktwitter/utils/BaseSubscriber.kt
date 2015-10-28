package com.backstopmedia.kotlin.ktwitter.utils

import rx.Subscriber

open class BaseSubscriber<T>() : Subscriber<T>() {

    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
    }

    override fun onNext(t: T) {
    }
}