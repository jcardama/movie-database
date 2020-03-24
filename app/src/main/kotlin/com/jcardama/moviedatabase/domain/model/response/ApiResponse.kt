package com.jcardama.moviedatabase.domain.model.response

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @SerializedName("results")
    @Expose
    var results: T? = null

    override fun toString(): String {
        return Gson().toJson(this)
    }
}
