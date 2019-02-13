package com.satansminion.myhell.therockapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.satansminion.myhell.therockapp.R
import com.satansminion.myhell.therockapp.data.SavedSong
import com.squareup.picasso.Picasso

/**
 *
 * Created by Satans-Minion
 * Sat, 09 February 2019 at 6:56 AM.
 * Project: TheRockApp
 * com.satansminion.myhell.therockapp.adapter
 *
 */
private const val TAG = "FavoritesAdapter"

class FavoritesAdapter : ListAdapter<SavedSong, FavoritesAdapter.FavViewHolder>(FavSongListDiffCallback()) {
    private var songList: List<SavedSong>? = null

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        with(holder){
            if (songList!!.isEmpty()) {
                songArtist.setText(R.string.tv_no_data_text)
                songTitle.setText(R.string.tv_no_data_text)
                songDateTime.setText(R.string.tv_no_data_text)
            } else {
                val songItem = songList!![position]
                songTitle.text = songItem.title
                songArtist.text = songItem.artist
                val newTime = songItem.played_time.substring(0, 5)
                val datetime = "$newTime on ${songItem.played_date}"
                songDateTime.text = datetime

                Picasso.get()
                    .load(songItem.artwork)
                    .placeholder(R.drawable.ic_broken_image)
                    .into(artistImage)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return FavViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (songList == null) 0 else songList!!.size
    }

    fun setSongList(Songs: List<SavedSong>): Boolean {
        Log.d(TAG, "setSongList")
        songList = Songs
        notifyDataSetChanged()
        return true
    }

    fun getSongAt(position: Int): SavedSong {
        return songList!![position]
    }

    class FavViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var artistImage: ImageView = view.findViewById(R.id.imageArtwork)
        var songDateTime: TextView = view.findViewById(R.id.tvTimeDate)
        var songArtist: TextView = view.findViewById(R.id.tvArtist)
        var songTitle: TextView = view.findViewById(R.id.tvSongTitle)
    }
}

private class FavSongListDiffCallback : DiffUtil.ItemCallback<SavedSong>() {
    override fun areItemsTheSame(oldItem: SavedSong, newItem: SavedSong): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SavedSong, newItem: SavedSong): Boolean {
        return oldItem == newItem
    }
}