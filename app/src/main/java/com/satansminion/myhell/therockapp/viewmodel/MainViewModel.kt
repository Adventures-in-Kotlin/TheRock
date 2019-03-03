package com.satansminion.myhell.therockapp.viewmodel

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satansminion.myhell.therockapp.data.Repository
import com.satansminion.myhell.therockapp.data.SavedSong
import com.satansminion.myhell.therockapp.data.Song
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(private val repository: Repository) : ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
//    private val viewModelJob = Job()
    /**
     * This is the scope for all coroutines launched by [MainViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
//    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }

    /**
     * Moving livedata to variables instead of the
     * old function calls
     */
    val songJsonData = repository.songJsonData
    val savedSongData = repository.getAllSavedSongs()

    lateinit var connectivityManager: ConnectivityManager

    private val _spinner = MutableLiveData<Boolean>()

    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _snackBar = MutableLiveData<String>()

    val snackbar: LiveData<String>
        get() = _snackBar

    fun refreshDataFromUrl() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "*refreshDataFromUrl")
                if (checkNetworkConnection()) {
                    Log.d(TAG, "*refreshDataFromUrl inside")
                    _spinner.value = true
                    repository.getNewJsonData()
                } else {
                    _snackBar.value = "No network connection available"
                }
            } catch (ex: Exception) {
                _snackBar.value = ex.message
            } finally {
                _spinner.value = false
                onSnackbarShown()
            }
        }
    }

    fun insertSavedSong(song: Song) {
        val savedSong = SavedSong(song.title, song.artist, song.played_date, song.played_time, song.artwork)
        Log.d(TAG, "Got to ViewModel")
        viewModelScope.launch { repository.insertSavedSong(savedSong) }
        _snackBar.value = "Saved: ${song.title}"
    }

    fun deleteAllSavedSongs() = viewModelScope.launch { repository.deleteAllSavedSongs() }

    fun checkNetworkConnection(): Boolean {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    fun onSnackbarShown() {
        _snackBar.value = null
    }
}
