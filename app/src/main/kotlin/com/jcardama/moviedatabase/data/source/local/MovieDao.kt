package com.jcardama.moviedatabase.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jcardama.moviedatabase.data.model.MovieModel

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(subject: MovieModel?): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(subject: List<MovieModel>?)

    @Query("SELECT * FROM MovieModel WHERE id = :id")
    suspend fun getById(id: Long): MovieModel?

    @Query("SELECT * FROM MovieModel WHERE isFromSearch = 0")
    suspend fun getAll(): List<MovieModel>?

    @Query("SELECT * FROM MovieModel WHERE title LIKE '%' || :query || '%'")
    suspend fun searchByTitle(query: String): List<MovieModel>?

    @Query("SELECT * FROM MovieModel WHERE searched = 1 ORDER BY timestamp DESC LIMIT 5")
    suspend fun getSearched(): List<MovieModel>?

    @Query("SELECT * FROM MovieModel WHERE favorite = 1")
    suspend fun getFavorites(): List<MovieModel>?

    @Query("SELECT * FROM MovieModel WHERE addedToWatchList = 1")
    suspend fun getWatchList(): List<MovieModel>?

    @Query("UPDATE MovieModel SET favorite=:isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE MovieModel SET addedToWatchList=:isInWatchList WHERE id = :id")
    suspend fun setWatchList(id: Long, isInWatchList: Boolean)

    @Query("UPDATE MovieModel SET searched=1 WHERE id = :id")
    suspend fun setAsSearched(id: Long)
}
