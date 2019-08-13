package com.jcardama.moviedatabase.util.extension

import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<MutableList<T>>.plus(value: T) {
    val values = this.value ?: arrayListOf()
    values.add(value)
    this.value = values
}

operator fun <T> MutableLiveData<MutableList<T>>.minus(value: T?) {
    val values = this.value ?: arrayListOf()
    values.remove(value)
    this.value = values
}

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}

operator fun <T> MutableLiveData<MutableList<T>>.minusAssign(values: List<T?>) {
    val value = this.value ?: arrayListOf()
    value.removeAll(values)
    this.value = value
}
