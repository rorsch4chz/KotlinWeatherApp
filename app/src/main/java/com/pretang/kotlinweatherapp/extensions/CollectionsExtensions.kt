package com.pretang.kotlinweatherapp.extensions

/**
 * @author baizhou
 * @data 2018/1/9
 * @description
 */

fun <K, V : Any> Map<K, V?>.toVarargArray(): Array<Pair<K, V>> =
        map({ Pair(it.key, it.value!!) }).toTypedArray()

inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?): R {
    for (element in this) {
        val result = predicate(element)
        if (result != null) return result
    }
    throw NoSuchElementException("No element matching predicate was found.")
}