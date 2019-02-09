package com.satansminion.myhell.therockapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
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

class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var artistImage: ImageView = view.findViewById(R.id.imageArtwork)
    var songDateTime: TextView = view.findViewById(R.id.tvTimeDate)
    var songArtist: TextView = view.findViewById(R.id.tvArtist)
    var songTitle: TextView = view.findViewById(R.id.tvSongTitle)
}

private const val TAG = "SongAdapter"

class SongAdapter(val frag: Fragment) : RecyclerView.Adapter<FavViewHolder>() {

    private var songList: ArrayList<Song>? = null
    private var _frag: Fragment? = null

    private var listener: AdapterView.OnItemClickListener? = null

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        if (songList!!.isEmpty()) {
            holder.songArtist.setText(R.string.tv_no_data_text)
            holder.songTitle.setText(R.string.tv_no_data_text)
            holder.songDateTime.setText(R.string.tv_no_data_text)
        } else {
            val songItem = songList!![position]
            holder.songTitle.text = songItem.title
            holder.songArtist.text = songItem.artist
            val newTime = songItem.played_time.substring(0, 5)
            val datetime = "$newTime on ${songItem.played_date}"
            holder.songDateTime.text = datetime

            Picasso.get()
                .load(songItem.artwork)
                .placeholder(R.drawable.ic_broken_image)
                .into(holder.artistImage)
        }
    }

    init {
        _frag = frag
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        parent.background.alpha = 0


        return FavViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (songList == null) 0 else songList!!.size
    }

    fun setSongList(Songs: ArrayList<Song>): Boolean {
        Log.d(TAG, "setSongList")
        songList = Songs
        notifyDataSetChanged()
        return true
    }

    fun getSongAt(position: Int): Song {
        return songList!![position]
    }


}