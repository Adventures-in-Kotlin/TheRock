package com.satansminion.myhell.therockapp.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satansminion.myhell.therockapp.R
import com.satansminion.myhell.therockapp.adapter.SongAdapter
import com.satansminion.myhell.therockapp.data.Song
import com.satansminion.myhell.therockapp.utilities.InjectorUtils
import com.satansminion.myhell.therockapp.utilities.RecyclerItemClickListener
import com.satansminion.myhell.therockapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Runnable
import java.util.ArrayList
import java.util.Random

private const val TAG = "MainFragment"

class MainFragment : Fragment(), RecyclerItemClickListener.OnRecyclerClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SongAdapter
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private lateinit var mRandom: Random

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.main_fragment, container, false)

        val factory = InjectorUtils.provideViewModelFactory(this.requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        mRandom = Random()
        mHandler = Handler()

        val recycler: RecyclerView? = view?.findViewById(R.id.songView)
        adapter = SongAdapter()
        recycler?.layoutManager = LinearLayoutManager(context)
//        recycler?.setHasFixedSize(true)
        recycler?.adapter = adapter

        recycler?.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recycler,this))

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getSongsRefresh()
        refreshView.setColorSchemeResources(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3,
            R.color.refresh_progress_4
        )

        refreshView.setOnRefreshListener {
            try {
//                Log.d(TAG, "onActivityCreated: refreshView.setOnRefreshListener")
                if (isNetworkConnected()) {
                    mRunnable = Runnable {
//                        Log.d(TAG, "Swiping to refresh")
                        getSongsRefresh()
//                        Log.d(TAG, "mRunnable: Refreshing??????")
                    }
                    mHandler.post(mRunnable)
//                    Log.d(TAG, "mHandler: Refreshing??????")
                }
//                Log.d(TAG, "Outside runnable: Refreshing??????")
            } catch (ex: Exception) {
                Log.e(TAG, "refreshView.setOnRefreshListener - ${ex.message}")
            }
        }
    }

    override fun onItemClick(view: View, position: Int) {
//        Log.d(TAG, "onItemClick: clicked")
        val theSong = adapter.getSongAt(position)
        viewModel.insertSavedSong(theSong)
        Toast.makeText(context, "Saved: ${theSong.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {

//        Doing nothing on the long click

//        Log.d(TAG, "onItemLongClick: clicked")
//        val theSong = adapter.getSongAt(position)
//        Toast.makeText(context, "Long tap song is ${theSong.title}", Toast.LENGTH_SHORT).show()

    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        if (!isConnected) {
            Toast.makeText(context, "No network connection available", Toast.LENGTH_LONG).show()
        }
        return isConnected
    }

    private fun getSongsRefresh() {
        refreshView.isRefreshing = true
        if (isNetworkConnected()) {
            Log.d(TAG, "getSongsRefresh: start")
            Log.d(TAG, "getSongsRefresh: calling viewModel.getJsonResults()")

//            refreshView.isRefreshing = true
            val updateUI = Observer<ArrayList<Song>> { songList ->
                refreshView.isRefreshing = true
                adapter.setSongList(songList)

                refreshView.isRefreshing = false
            }
            viewModel.getJsonData().observe(this, updateUI)
        }
        refreshView.isRefreshing = false
    }
}
