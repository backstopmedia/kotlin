package com.backstopmedia.kotlin.chapter6.generics

class Bounds {

    fun test() {
        val ints = rocketListOf(1, 2, 3)
        val objects: RocketList<Any> = ints
    }

}


interface RocketList<out T> {
    fun get(i: Int): T
}

fun <T> rocketListOf(vararg items: T): RocketList<T> {
    return object: RocketList<T> {
        override fun get(i: Int): T = items[i]
    }
}