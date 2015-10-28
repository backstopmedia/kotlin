package com.backstopmedia.kotlin.chapter6.collections

import org.junit.Test

public class Maps {

    /**
     * Map has a pretty nice initializer syntax.
     * Technically it leverages extension methods to create [Pair] objects.
     */
    @Test fun mapInit() {
        val map = mapOf(
                1L to "one",
                2L to "two")
        println("Here's some numbers: $map")
    }

}