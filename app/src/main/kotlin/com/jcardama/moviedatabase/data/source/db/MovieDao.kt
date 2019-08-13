package com.jcardama.moviedatabase.data.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jcardama.moviedatabase.domain.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(subject: Movie?)

    @Query("SELECT * FROM Movie")
    suspend fun getAll() : List<Movie>?

    @Query("SELECT * FROM Movie WHERE favorite = 1")
    suspend fun getFavorites() : List<Movie>?

    @Query("SELECT * FROM Movie WHERE addedToWatchList = 1")
    suspend fun getWatchList() : List<Movie>?
}
