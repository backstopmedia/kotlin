package com.backstopmedia.kotlin.chapter7

import org.junit.Test

@Suppress("UNUSED_VARIABLE")
class PatternMatchingTest {
    @Test fun typeMatch() {
        val x: Any = "hello"
        when (x) {
            is Int -> println("Int[$x]")
            is List<*> -> println("List[${x.size()}]")
            is String -> println("String[${x.length()}]")
        }
    }

    @Test fun elseMatch() {
        val x: Any = "hello"
        when (x) {
            is Int -> println("Int[$x]")
            is List<*> -> println("List[${x.size()}]")
            is String -> println("String[${x.length()}]")
            else -> println("unknown [$x]")
        }
    }

    @Test fun sealedMatch() {
        fun number(foo: Foo): Int = when (foo) {
            is Foo.Bar -> 3
            is Foo.Baz -> 6
        }

        println(number(Foo.Bar("hi")))
    }

}

sealed class Foo {
    class Bar(value: String) : Foo()
    class Baz(value: Int) : Foo()
}


