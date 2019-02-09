package com.satansminion.myhell.therockapp.utilities

import android.content.Context
import com.satansminion.myhell.therockapp.data.Repository
import com.satansminion.myhell.therockapp.data.SongDatabase
import com.satansminion.myhell.therockapp.ui.main.SongViewModelFactory

/**
 *
 * Created by Satans-Minion
 * Fri, 08 February 2019 at 4:43 PM.
 * Project: TheRockApp
 * com.satansminion.myhell.therockapp.utilities
 *
 */
object InjectorUtils {

    fun provideViewModelFactory(context: Context): SongViewModelFactory {
        val repository = Repository.getInstance(SongDatabase.getInstance(context)!!.songDao())
        return SongViewModelFactory(repository)
    }
}