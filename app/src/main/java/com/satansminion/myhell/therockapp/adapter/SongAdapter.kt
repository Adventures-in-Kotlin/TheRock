package com.satansminion.myhell.therockapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.satansminion.myhell.therockapp.R
import com.satansminion.myhell.therockapp.data.Song
import com.squareup.picasso.Picasso

/**
 *
 * Created by Satans-Minion on 27/01/2019
 * Project: TheRockApp
 *
 */

private const val TAG = "SongAdapter"

class SongAdapter : ListAdapter<Song, SongAdapter.SongViewHolder>(SongListDiffCallback()) {

    private var songList: List<Song>? = null

    private var listener: AdapterView.OnItemClickListener? = null

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
//        parent.background.alpha = 0
        return SongViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (songList == null) 0 else songList!!.size
    }

    fun setSongList(Songs: List<Song>): Boolean {
        Log.d(TAG, "setSongList")
        songList = Songs
        notifyDataSetChanged()
        return true
    }

    fun getSongAt(position: Int): Song {
        return songList!![position]
    }

    class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var artistImage: ImageView = view.findViewById(R.id.imageArtwork)
        var songDateTime: TextView = view.findViewById(R.id.tvTimeDate)
        var songArtist: TextView = view.findViewById(R.id.tvArtist)
        var songTitle: TextView = view.findViewById(R.id.tvSongTitle)
    }
}

private class SongListDiffCallback : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }
}