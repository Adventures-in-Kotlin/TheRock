package com.satansminion.myhell.therockapp.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.satansminion.myhell.therockapp.R
import com.satansminion.myhell.therockapp.adapter.SongAdapter
import com.satansminion.myhell.therockapp.utilities.InjectorUtils
import com.satansminion.myhell.therockapp.utilities.RecyclerItemClickListener
import com.satansminion.myhell.therockapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

private const val TAG = "MainFragment"

class MainFragment : Fragment(), RecyclerItemClickListener.OnRecyclerClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.main_fragment, container, false)

        val factory = InjectorUtils.provideViewModelFactory(this.requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        viewModel.connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val recycler: RecyclerView = view.findViewById(R.id.songView)

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)

        adapter = SongAdapter()
        recycler.adapter = adapter

        recycler.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recycler, this))

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.spinner.observe(this, Observer { value ->
            refreshView.isRefreshing = value
        })

        viewModel.songJsonData.observe(this, Observer { songList ->
            songList?.let {
                Log.d(TAG, "*songJsonData Observer")
                Log.d(TAG, "*songJsonData Observer ${it.size}")

            }
            adapter.setSongList(songList)
        })

        viewModel.snackbar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(main, text, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShown()
            }
        })

        refreshView.setOnRefreshListener {
            //            if (isNetworkConnected()) {
            refreshView.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3,
                R.color.refresh_progress_4
            )
            viewModel.refreshDataFromUrl()
        }
        viewModel.refreshDataFromUrl()
    }

    override fun onItemClick(view: View, position: Int) {
//        Log.d(TAG, "onItemClick: clicked")
        val theSong = adapter.getSongAt(position)
        viewModel.insertSavedSong(theSong)
    }

    override fun onItemLongClick(view: View, position: Int) {
//        Doing nothing on the long click
    }

}
