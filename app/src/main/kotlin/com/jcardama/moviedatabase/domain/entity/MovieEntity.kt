package com.jcardama.moviedatabase.domain.entity

import com.google.gson.Gson

class MovieEntity(
        var id: Long,
        var voteAverage: Double?,
        var title: String?,
        var posterPath: String?,
        var backdropPath: String?,
        var overview: String?,
        var releaseDate: String?,
        var favorite: Boolean,
        var addedToWatchList: Boolean,
        var isFromSearch: Boolean,
        var searched: Boolean
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}
