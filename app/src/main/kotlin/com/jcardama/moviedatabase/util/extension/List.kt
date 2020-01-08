package com.jcardama.moviedatabase.util.extension

fun <T> MutableList<T>.replace(newValue: T, block: (T) -> Boolean): MutableList<T> {
    return map {
        if (block(it)) newValue else it
    }.toMutableList()
}

fun <T> MutableList<T>.addOrRemoveIf(value: T, block: () -> Boolean): MutableList<T> {
    when(block()) {
        true -> remove(value)
        else -> when(contains(value)) {
            true -> replace(value) { it == value }
            else -> add(value)
        }
    }
    return this
}
