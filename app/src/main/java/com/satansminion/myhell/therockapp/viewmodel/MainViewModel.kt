package com.satansminion.myhell.therockapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.satansminion.myhell.therockapp.data.Repository
import com.satansminion.myhell.therockapp.data.SavedSong
import com.satansminion.myhell.therockapp.data.Song

private const val TAG = "MainViewModel"

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getJsonData(): LiveData<ArrayList<Song>> {
        Log.d(TAG, "getJsonData: Got Here")
//        return repository.getJsonData()

        return repository.getNewJsonData()
    }

//    fun getJsonResults() = repository.searchResults//: LiveData<ArrayList<Song>>{
//        return jsonResults
//    }

    fun insertSavedSong(song: Song) {
        val savedSong = SavedSong(song.title, song.artist, song.played_date, song.played_time, song.artwork)
        Log.d(TAG, "Got to ViewModel")
        repository.insertSavedSong(savedSong)
    }

    fun getAllSavedSongs(): LiveData<List<SavedSong>> = repository.getAllSavedSongs()

    fun deleteAllSavedSongs() = repository.deleteAllSavedSongs()
}
