package com.satansminion.myhell.therockapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.satansminion.myhell.therockapp.data.Repository
import com.satansminion.myhell.therockapp.data.SavedSong
import com.satansminion.myhell.therockapp.data.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(private val repository: Repository) : ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    /**
     * This is the scope for all coroutines launched by [MainViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Moving livedata to variables instead of the
     * old function calls
     */
    val songJsonData = repository.getNewJsonData()
    val savedSongData = repository.getAllSavedSongs()


//    fun getJsonData(): LiveData<List<Song>>  {
//        Log.d(TAG, "getJsonData: Got Here")
//
//
//        return repository.getNewJsonData()
//    }


    fun insertSavedSong(song: Song) {
        val savedSong = SavedSong(song.title, song.artist, song.played_date, song.played_time, song.artwork)
        Log.d(TAG, "Got to ViewModel")
        viewModelScope.launch { repository.insertSavedSong(savedSong) }
    }

//    fun getAllSavedSongs(): LiveData<List<SavedSong>> = repository.getAllSavedSongs()

    fun deleteAllSavedSongs() = viewModelScope.launch { repository.deleteAllSavedSongs() }
}
