package com.dicoding.salsahava.seeara.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.salsahava.seeara.data.entity.RecordingEntity


@Database(entities = [RecordingEntity::class], version = 1, exportSchema = false)
abstract class RecordingDatabase : RoomDatabase() {

    abstract fun recordingDao(): RecordingDao

    companion object {
        @Volatile
        private var INSTANCE: RecordingDatabase? = null

        fun getDatabase(context: Context): RecordingDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RecordingDatabase::class.java,
                    "record.db"
                ).build().apply { INSTANCE = this }
            }
    }
}

