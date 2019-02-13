package com.satansminion.myhell.therockapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 *
 * Created by Satans-Minion
 * Fri, 08 February 2019 at 4:01 PM.
 * Project: TheRockApp
 * com.satansminion.myhell.therockapp.data
 *
 */
@Dao
interface SongDao {
    @Insert
    fun insert(savedSong: SavedSong)

    @Query("DELETE FROM rockSong")
    fun deleteAllSavedSongs()

    @Query("SELECT * FROM rockSong ORDER BY played_date, played_time DESC")
    fun getAllSavedSongs() : LiveData<List<SavedSong>>

    @Query("SELECT * FROM rockSong WHERE title = :title AND artist = :artist")
    fun getByTitleAndArtist(title: String, artist: String) : List<SavedSong>

}