package com.practicum.playlistmaker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.data.dao.PlaylistRepository
import com.practicum.playlistmaker.data.dao.TrackRepository
import com.practicum.playlistmaker.data.entity.PlaylistEntity
import com.practicum.playlistmaker.data.entity.PlaylistTrackCrossRef
import com.practicum.playlistmaker.data.entity.TrackEntity

@Database(
    version = 1,
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRef::class
    ],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistRepository
    abstract fun trackDao(): TrackRepository

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "playlistmaker.db"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}