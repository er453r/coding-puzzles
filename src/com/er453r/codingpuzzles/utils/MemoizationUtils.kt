package com.er453r.codingpuzzles.utils

fun <A, O> memoize(function: ((A) -> O).(A) -> O): (A) -> O =
    object : MemoizedFunction<Params1<A>, O>(), (A) -> O {
        override fun invoke(a: A): O = invokeCached(Params1(a))
        override fun doInvoke(p: Params1<A>): O = function(this, p.a)
    }
fun <A, B, C, O> memoize(function: ((A, B, C) -> O).(A, B, C) -> O): (A, B, C) -> O =
    object : MemoizedFunction<Params3<A, B, C>, O>(), (A, B, C) -> O {
        override fun invoke(a: A, b: B, c: C): O = invokeCached(Params3(a, b, c))
        override fun doInvoke(p: Params3<A, B, C>): O = function(this, p.a, p.b, p.c)
    }

private abstract class MemoizedFunction<I, O> {
    private val cache = mutableMapOf<I, O>()

    fun invokeCached(param: I) = cache.getOrPut(param) { doInvoke(param) }

    abstract fun doInvoke(p: I): O
}

private data class Params1<A>(val a: A)
private data class Params3<A, B, C>(val a: A, val b: B, val c: C)
