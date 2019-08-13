package com.jcardama.moviedatabase.util.extension

import com.google.gson.JsonArray

inline fun jsonArray(initFun: JsonArray.() -> Unit) = JsonArray().apply(initFun)
