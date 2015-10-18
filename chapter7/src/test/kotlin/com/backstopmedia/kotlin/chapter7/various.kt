package com.backstopmedia.kotlin.chapter7

import org.junit.Test

@Suppress("UNUSED_VARIABLE")
class VariousTest {

    @Test fun letFn() {
        val x = Point(1, 2).let { it.x }
    }

    @Test fun withFn() {
        val x = with(Point(1, 2)) { x }
    }

    @Test fun applyFn() {
        val point = Point(1, 2).apply { x = 3 }
    }

    @Test fun runFn() {
        val fn = fun(p: Point) = { p.x = p.x * 2; p }

        val point = Point(1, 2).run(fn)
    }


}

data class Point(var x: Int, var y: Int)

