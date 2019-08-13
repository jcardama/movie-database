package com.jcardama.moviedatabase.util.extension

import com.google.gson.JsonObject

inline fun jsonObject(initFun: JsonObject.() -> Unit) = JsonObject().apply(initFun)
