@file:Suppress("MemberVisibilityCanBePrivate")

package com.jcardama.moviedatabase.domain.model.base

import com.google.gson.Gson

open class BaseModel {
    var refreshTime: Long = 86400000 //1 Day
    var timestamp: Long = 0L

    fun expired(): Boolean = System.currentTimeMillis() - timestamp > refreshTime

    override fun toString(): String {
        return Gson().toJson(this)
    }
}
