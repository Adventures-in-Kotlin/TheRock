package com.satansminion.myhell.therockapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.satansminion.myhell.therockapp.data.Repository
import com.satansminion.myhell.therockapp.viewmodel.MainViewModel

/**
 *
 * Created by Satans-Minion
 * Fri, 08 February 2019 at 4:46 PM.
 * Project: TheRockApp
 * com.satansminion.myhell.therockapp.ui.main
 *
 */
class SongViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}