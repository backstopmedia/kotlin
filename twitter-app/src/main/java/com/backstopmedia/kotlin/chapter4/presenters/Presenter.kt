package com.backstopmedia.kotlin.chapter4.presenters

interface Presenter<V> {

    fun takeView(view: V)

    fun dropView(view: V)
}