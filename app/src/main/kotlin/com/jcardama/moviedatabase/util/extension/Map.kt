package com.jcardama.moviedatabase.util.extension

fun <K, V> MutableMap<K, V>.reversed() = HashMap<K, V>().also { newMap ->
	entries.reversed().forEach { newMap[it.key] = it.value }
}

fun <K, V> LinkedHashMap<K, V>.reversed() = LinkedHashMap<K, V>().also { newMap ->
	entries.reversed().forEach { newMap[it.key] = it.value }
}

fun <K, V> Map<K, V>?.toHashMap() = this?.let { HashMap<K, V>(this) } ?: hashMapOf()
