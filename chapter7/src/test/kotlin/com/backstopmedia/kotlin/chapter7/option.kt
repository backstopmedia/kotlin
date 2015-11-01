package com.backstopmedia.kotlin.chapter7

import org.junit.Test


@Suppress("UNUSED_VARIABLE")
class OptionTest {


    @Test fun optionWhen() {
        val values = mapOf(1 to "one", 2 to "two", 3 to "three")
        val option = values.optional(4)
        val msg = when (option) {
            is Option.Some -> "Some[${option.value}]"
            is Option.None -> "Nothing"
        }
        println(msg)
    }

    @Test fun optionMap() {
        val values = mapOf(1 to 3, 2 to 6)
        val opt1 = values.optional(1)
        val opt2 = opt1.map { it * 2 }
        val opt3 = opt2.map { it + 4 }

        when (opt3) {
            is Option.Some -> println(opt3.value)
        }
    }

    @Test fun optionApply() {
        val optionVal = Option.of(3)
        val optionFn = Option.of({ n: Int -> n * 2 })
        val option = optionVal.apply(optionFn)

        when (option) {
            is Option.Some -> println(option.value)
        }
    }

    @Test fun optionBind() {
        val optionVal = Option.of(4)
        val fn = fun(n: Int) = if (n and 1 == 0) Option.Some(n) else Option.None.of<Int>()
        val option = optionVal.bind(fn)

        when (option) {
            is Option.Some -> println(option.value)
        }
    }

}


sealed class Option<T : Any> {
    companion object {
        fun <T : Any> of(value: T?): Option<T> =
                if (value != null) Option.Some(value) else Option.None.of()
    }

    abstract fun <R : Any> map(functor: (T) -> R): Option<R>
    abstract fun <R : Any> apply(applicative: Option<(T) -> R>): Option<R>
    abstract fun <R : Any> bind(fn: (T) -> Option<R>): Option<R>

    class Some<T : Any>(val value: T) : Option<T>() {

        override fun <R : Any> apply(applicative: Option<(T) -> R>): Option<R> = when (applicative) {
            is Some -> Some(applicative.value(value))
            is None -> None.of()
        }

        override fun <R : Any> map(functor: (T) -> R): Some<R> = Some(functor(value))
        override fun <R : Any> bind(fn: (T) -> Option<R>): Option<R> = fn(value)
    }


    class None<T : Any> private constructor() : Option<T>() {


        companion object {
            private val instance = None<Nothing>()

            @Suppress("CAST_NEVER_SUCCEEDS")
            fun <T : Any> of(): None<T> = instance as None<T>
        }

        override fun <R : Any> apply(applicative: Option<(T) -> R>): None<R> = None.of()
        override fun <R : Any> map(functor: (T) -> R): None<R> = None.of()
        override fun <R : Any> bind(fn: (T) -> Option<R>): None<R> = None.of()
    }
}

fun <K, V : Any> Map<K, V>.optional(key: K): Option<V> = Option.of(this[key])