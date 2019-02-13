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
import com.satansminion.myhell.therockapp.utilities.InjectorUtils
import com.satansminion.myhell.therockapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*


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

        val recycler: RecyclerView = view.findViewById(R.id.favoritesView)
        adapter = FavoritesAdapter()
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_delete.setOnClickListener {
            viewModel.deleteAllSavedSongs()
            val recycler: RecyclerView = view.findViewById(R.id.favoritesView)
            recycler.visibility = View.GONE
            tv_empty_list.visibility = View.VISIBLE
        }
        getSongsRefresh()
    }


    private fun getSongsRefresh() {
        viewModel.savedSongData.observe(viewLifecycleOwner, Observer { results ->
            if (results != null && results.isNotEmpty()){
                tv_empty_list.visibility = View.GONE
                adapter.setSongList(results)
            }else{
                tv_empty_list.visibility = View.VISIBLE
            }

        })

    }

    override fun onStop() {
        super.onStop()
        viewModel.savedSongData.removeObservers(viewLifecycleOwner)
    }
}
