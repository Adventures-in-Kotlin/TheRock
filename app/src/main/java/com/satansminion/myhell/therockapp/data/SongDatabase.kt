package com.satansminion.myhell.therockapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *
 * Created by Satans-Minion
 * Fri, 08 February 2019 at 4:17 PM.
 * Project: TheRockApp
 * com.satansminion.myhell.therockapp.data
 *
 */
@Database(entities = [SavedSong::class], version = 1)
abstract class SongDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {
        private var INSTANCE: SongDatabase? = null

        internal fun getInstance(context: Context): SongDatabase? {
            if (INSTANCE == null) {
                synchronized(SongDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder<SongDatabase>(
                                context.applicationContext,
                                SongDatabase::class.java,
                                "SongDatabase.db"
                            ).build()
                    }
                }
            }

            return INSTANCE
        }
    }
}