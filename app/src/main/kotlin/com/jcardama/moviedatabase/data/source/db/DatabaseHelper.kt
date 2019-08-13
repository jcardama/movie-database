package com.jcardama.moviedatabase.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jcardama.moviedatabase.domain.model.Movie

@Database(entities = [], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "database.db"
        const val VERSION = 1
    }
}
