package com.satansminion.myhell.therockapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satansminion.myhell.therockapp.R
import com.satansminion.myhell.therockapp.adapter.FavoritesAdapter
import com.satansminion.myhell.therockapp.data.SavedSong
import com.satansminion.myhell.therockapp.utilities.InjectorUtils
import com.satansminion.myhell.therockapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

private const val TAG = "FavoritesFragment"

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        val factory = InjectorUtils.provideViewModelFactory(this.requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        val recycler: RecyclerView? = view?.findViewById(R.id.favoritesView)
        adapter = FavoritesAdapter()
        recycler?.layoutManager = LinearLayoutManager(context)
        recycler?.setHasFixedSize(true)
        recycler?.adapter = adapter

//        val favSongs = viewModel.getAllSavedSongs()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_delete.setOnClickListener {
            viewModel.deleteAllSavedSongs()
        }

        getSongsRefresh()
    }

//    override fun onStart() {
//        super.onStart()
//        getSongsRefresh()
//    }

    private fun getSongsRefresh() {

//            Log.d(TAG, "getSongsRefresh: start")
//            Log.d(TAG, "getSongsRefresh: calling viewModel.getJsonData()")
//            viewModel.getJsonData()

        val updateUI = Observer<List<SavedSong>> { favSongs ->
            if (favSongs.isEmpty()) {
//                Do something to notify user there are no songs saved to favorites
            }

            adapter.setSongList(favSongs)
        }

        viewModel.getAllSavedSongs().observe(this, updateUI)
    }
}
