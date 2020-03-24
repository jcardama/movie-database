package com.jcardama.moviedatabase.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jcardama.moviedatabase.data.model.MovieModel

@Database(entities = [
    MovieModel::class
], version = AppDatabase.VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "database.db"
        const val VERSION = 5
    }

    abstract fun movieDao(): MovieDao
}
