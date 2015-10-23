package com.backstopmedia.kotlin.chapter6

import org.junit.Test

/**
 * Created by Aaron Sarazan on 10/22/15
 * Copyright(c) 2015 Level, Inc.
 */
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