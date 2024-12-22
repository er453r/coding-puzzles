package com.er453r.codingpuzzles.utils

fun <A, O> memoize(function: ((A) -> O).(A) -> O): (A) -> O =
    object : MemoizedFunction<Params1<A>, O>(), (A) -> O {
        override fun invoke(a: A): O = invokeCached(Params1(a))
        override fun doInvoke(p: Params1<A>): O = function(this, p.a)
    }

fun <A, B, O> memoize(function: ((A, B) -> O).(A, B) -> O): (A, B) -> O =
    object : MemoizedFunction<Params2<A, B>, O>(), (A, B) -> O {
        override fun invoke(a: A, b: B): O = invokeCached(Params2(a, b))
        override fun doInvoke(p: Params2<A, B>): O = function(this, p.a, p.b)
    }

fun <A, B, C, O> memoize(function: ((A, B, C) -> O).(A, B, C) -> O): (A, B, C) -> O =
    object : MemoizedFunction<Params3<A, B, C>, O>(), (A, B, C) -> O {
        override fun invoke(a: A, b: B, c: C): O = invokeCached(Params3(a, b, c))
        override fun doInvoke(p: Params3<A, B, C>): O = function(this, p.a, p.b, p.c)
    }

fun <A, B, C, D, O> memoize(function: ((A, B, C, D) -> O).(A, B, C, D) -> O): (A, B, C, D) -> O =
    object : MemoizedFunction<Params4<A, B, C, D>, O>(), (A, B, C, D) -> O {
        override fun invoke(a: A, b: B, c: C, d: D): O = invokeCached(Params4(a, b, c, d))
        override fun doInvoke(p: Params4<A, B, C, D>): O = function(this, p.a, p.b, p.c, p.d)
    }

fun <A, B, C, D, E, O> memoize(function: ((A, B, C, D, E) -> O).(A, B, C, D, E) -> O): (A, B, C, D, E) -> O =
    object : MemoizedFunction<Params5<A, B, C, D, E>, O>(), (A, B, C, D, E) -> O {
        override fun invoke(a: A, b: B, c: C, d: D, e: E): O = invokeCached(Params5(a, b, c, d, e))
        override fun doInvoke(p: Params5<A, B, C, D, E>): O = function(this, p.a, p.b, p.c, p.d, p.e)
    }

private abstract class MemoizedFunction<I, O> {
    private val cache = mutableMapOf<I, O>()

    fun invokeCached(param: I) = cache.getOrPut(param) { doInvoke(param) }

    abstract fun doInvoke(p: I): O
}

private data class Params1<A>(val a: A)
private data class Params2<A, B>(val a: A, val b: B)
private data class Params3<A, B, C>(val a: A, val b: B, val c: C)
private data class Params4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)
private data class Params5<A, B, C, D, E>(val a: A, val b: B, val c: C, val d: D, val e: E)
