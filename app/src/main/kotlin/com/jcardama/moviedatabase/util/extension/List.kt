package com.jcardama.moviedatabase.util.extension

fun <TModel> List<TModel>.toArrayList(): ArrayList<TModel> {
    return ArrayList(this)
}
