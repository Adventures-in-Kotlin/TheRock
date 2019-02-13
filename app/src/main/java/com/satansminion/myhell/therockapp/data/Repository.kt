package com.satansminion.myhell.therockapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.StringReader
import java.net.URL
import java.util.Calendar

/**
 *
 * Created by Satans-Minion on 27/01/2019
 * Project: TheRockApp
 *
 */
private const val TAG = "Repository"

class Repository private constructor(private val songDao: SongDao) {

    private var jsonDataUrlBase = ""

    //    Create a singleton of the Dao
    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(songDao: SongDao) =
            instance ?: synchronized(this) {
                instance ?: Repository(songDao).also { instance = it }
            }
    }

    suspend fun insertSavedSong(savedSong: SavedSong) {
        withContext(Dispatchers.IO) {
//            Check if the song is already in the database
            val checkedSong = withContext(Dispatchers.Default) {
                songDao.getByTitleAndArtist(
                    savedSong.title,
                    savedSong.artist
                )
            }
//            if the song has already been saved to the database
//            don't bother to save it again
            if (checkedSong.isNullOrEmpty()){
                songDao.insert(savedSong)
            }


        }
    }

    //    fun getAllSavedSongs() = songDao.getAllSavedSongs()
    fun getAllSavedSongs(): LiveData<List<SavedSong>> {
        val savedSong = songDao.getAllSavedSongs()//  getSavedSongs()
        return savedSong
    }

//    suspend fun getSavedSongs(): LiveData<List<SavedSong>> =
//        runBlocking { withContext(Dispatchers.IO) { songDao.getAllSavedSongs() } }

    suspend fun deleteAllSavedSongs() = withContext(Dispatchers.IO) { songDao.deleteAllSavedSongs() }

    fun getNewJsonData(): LiveData<List<Song>> {
        Log.d(TAG, "Got to getNewJsonData")
        Log.d(TAG, "*******getNewJsonData: ${Thread.currentThread().name}")
        val jsonData = fetchUrlData()
        val liveSongData = MutableLiveData<List<Song>>()
        liveSongData.value = jsonData
        return liveSongData
    }

    init {
        jsonDataUrlBase =
            "https://radio-api.mediaworks.nz/radio-api/v2/station/therock/playedList?unique=true&fromUTC=${getDateFrom()}&toUTC=${getDateTo()}"
        Log.d(TAG, "init - URL: $jsonDataUrlBase")
    }

    private fun fetchUrlData(): List<Song> = runBlocking {
        //            Log.d(TAG, "Got to fetchUrlData")
//            withContextFetchData()
        return@runBlocking withContext(Dispatchers.Default) {
            Log.d(TAG, "*******fetchUrlData: ${Thread.currentThread().name}")
            withContextFetchData()
        }

    }

    private fun withContextFetchData(): List<Song> {
        Log.d(TAG, "Got to withContextFetchData")
        val songArray = arrayListOf<Song>()
        Log.d(TAG, "withContextFetchData: Starting")
        try {
            Log.d(TAG, "*******withContextFetchData: ${Thread.currentThread().name}")
            Log.d(TAG, "withContextFetchData: Try - $jsonDataUrlBase")
            val entireJson = URL(jsonDataUrlBase).readText()

            val klax = Klaxon()
            JsonReader(StringReader(entireJson)).use { reader ->
                reader.beginArray {
                    Log.d(TAG, "Beginning Loop")
                    while (reader.hasNext()) {
                        val song = klax.parse<Song>(reader)
                        songArray.add(song!!)
                    }

                }

            }
            Log.d(TAG, "*******withContextFetchData: ${Thread.currentThread().name}")
            Log.d(TAG, "Array Size: ${songArray.size}")
        } catch (ex: Exception) {
            Log.e(TAG, "Error: ${ex.message}")
        }

        Log.d(TAG, "Song count: ${songArray.size}")
        return songArray.toList()
    }

    private fun getDateTo(): String {
        val e = System.currentTimeMillis() / 1000
        Log.d(TAG, "Time to (Milliseconds): $e")
        return e.toString()
    }

    private fun getDateFrom(): String {

        val c: Calendar = Calendar.getInstance()
//        val now = c.timeInMillis

        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
//        val timePassed = now - c.timeInMillis
        Log.d(TAG, "Time from: ${c.timeInMillis}")
        return c.timeInMillis.toString()
    }
}