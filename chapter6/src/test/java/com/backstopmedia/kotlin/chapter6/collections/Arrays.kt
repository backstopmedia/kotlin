package com.backstopmedia.kotlin.chapter6.collections

import org.junit.Test

class Arrays {

    /**
     * You're tearing me apart with your amazing syntax, [Array]!
     * We are using [Array.asList] because Array doesn't have a good [toString] method.
     */
    @Test fun arrayInit() {
        val someStrings = arrayOf("Oh", "hai", "Mark!")
        println("someStrings is ${someStrings.asList()}")
    }

    @Test fun initNulls() {
        val initLater: Array<Any?> = arrayOfNulls(5)
        println("initLater is ${initLater.asList()}")
    }

    /**
     * This will be of boxed type Array<Integer> on the JVM
     * Read on to see how primitive arrays are handled.
     */
    @Test fun initBlock() {
        val blockArray: Array<Int> = Array(3) { it }
        println("Block array is ${blockArray.asList()}")
    }

    /**
     * The primitive array types, such as [LongArray], are technically
     * not subtypes of [Array], but they have the same methods.
     * This array will be represented as long[] on the JVM.
     */
    @Test fun primitives() {
        val longs = longArrayOf(1L, 2L, 3L)
        println("Long array is $longs")
    }
}