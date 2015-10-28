package com.backstopmedia.kotlin.ktwitter.presenters

interface Presenter<V> {

    fun takeView(view: V)

    fun dropView(view: V)
}