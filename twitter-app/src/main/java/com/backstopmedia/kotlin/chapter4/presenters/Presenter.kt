package com.backstopmedia.kotlin.chapter4.presenters

/**
 * Created by Tudor Luca on 14/10/15.
 */
interface Presenter<V> {

    fun takeView(view: V)

    fun dropView(view: V)
}