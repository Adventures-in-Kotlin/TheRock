package com.satansminion.myhell.therockapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * Created by Satans-Minion
 * Fri, 08 February 2019 at 4:03 PM.
 * Project: TheRockApp
 * com.satansminion.myhell.therockapp.data
 *
 */
@Entity(tableName = "rockSong")
data class SavedSong(val title: String, val artist: String, val played_date: String, val played_time: String, val artwork: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "$title - $artist - $played_date - $played_time"
    }
}