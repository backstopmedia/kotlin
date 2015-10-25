package com.backstopmedia.kotlin.chapter6.generics

import org.junit.Test


class Generics {

    /**
     * Our [description] extension method declares an upper bound of [Number].
     * This means that you can't use the method on anything that doesn't
     * inherit from or implement that class/interface.
     */
    @Test fun upperBounds() {
        val something = 100
        println(something.description())

        val nothing: Int? = null
        println(nothing.description())

        val notNumber = "You're tearing me apart"
//        println(notNumber.description()) // Not valid for this receiver.
    }

}

/**
 * A null-safe toString wrapper for numbers. Works on any kind of number, null or not.
 */
fun <T : Number> T?.description(): String? {
    return if (this == null) "null" else "${this.javaClass.simpleName}: $this"
}
