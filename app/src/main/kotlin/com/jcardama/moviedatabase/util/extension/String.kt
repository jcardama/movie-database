package com.jcardama.moviedatabase.util.extension

fun String.Companion.empty() = ""

fun Any?.toStringOrNull(): String? = when(this) {
    null -> null
    else -> this.toString()
}
