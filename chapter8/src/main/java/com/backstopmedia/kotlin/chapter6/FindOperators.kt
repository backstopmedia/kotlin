package com.backstopmedia.kotlin.chapter6

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewGroup
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Aaron Sarazan on 10/19/15
 * Copyright(c) 2015 Level, Inc.
 */


@Suppress("UNCHECKED_CAST")
operator fun <T : View> Activity.get(resId: Int): T {
    return findViewById(resId) as T
}

@Suppress("UNCHECKED_CAST")
operator fun <T : Fragment> FragmentManager.get(key: String): T? {
    return findFragmentByTag(key) as T
}

operator fun rx.subscriptions.CompositeSubscription.plus(other: rx.Subscription): Unit {
    add(other)
}

operator fun rx.Subscription.plusAssign(other: rx.Subscription): rx.subscriptions.CompositeSubscription {
    return rx.subscriptions.CompositeSubscription(this, other)
}

operator fun <T : View> ViewGroup.get(@IdRes resId: Int): T {
    return findViewById(resId) as T
}