@file:Suppress("unused")

package com.jcardama.moviedatabase.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jcardama.moviedatabase.domain.model.base.BaseModel

@Entity
class Movie: BaseModel() {
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    @SerializedName("video")
    @Expose
    var video: Boolean? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null
    @SerializedName("genre_ids")
    @Expose
    @Ignore
    var genreIds: List<Int>? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null
    var favorite: Boolean = false
    var addedToWatchList: Boolean = false
    var isFromSearch: Boolean = false
    var searched: Boolean = false

    override fun equals(other: Any?): Boolean {
        return other is Movie && this.id == other.id
    }

    override fun hashCode(): Int = id
}
