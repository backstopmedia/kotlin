package com.backstopmedia.kotlin.chapter7

import org.junit.Test

@Suppress("UNUSED_VARIABLE")
class VariousTest {

    @Test fun letFn() {

        val x = Point(1, 2).let {
            val p = Point(3,4)
            it.x + p.x
        }
    }

    @Test fun nullableLetFn() {

        val point : Point? = Point(1, 2)

        val x : Int? = point?.let {
            val p = Point(3,4)
            it.x + p.x
        }
    }

    @Test fun withFn() {
        val point3D : Point3D = with(Point(1, 2)) {
            Point3D(x, y, 3)
        }

    }

    @Test fun applyFn() {
        val point : Point = Point(1, 2).apply {
            val msg = "Point ($x, $y)"
            println(msg)
        }
    }


}

data class Point(val x: Int, val y: Int)

data class Point3D(val x: Int, val y: Int, val z: Int)

