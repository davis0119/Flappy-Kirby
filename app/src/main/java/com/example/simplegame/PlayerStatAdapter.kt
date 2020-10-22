package com.example.simplegame

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.playerstat_item.view.*

class PlayerStatAdapter(
    var activity: Activity,
    var playerList:List<PlayerStat>,
    var getPosition:(Int) -> Unit) : RecyclerView.Adapter<PlayerStatViewHolder>() {
    // create new contacts
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatViewHolder =
        PlayerStatViewHolder(
            LayoutInflater.from(activity).inflate(R.layout.playerstat_item, parent, false))
    // binds the player info to the viewholder
    override fun onBindViewHolder(holder: PlayerStatViewHolder, position: Int) =
        holder.bind(playerList[position], position, getPosition)

    //gets the number of items in the list
    override fun getItemCount() = playerList.size
}

    class PlayerStatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView = view.playerName
        var score: TextView = view.playerScore
        var view: View = view

        // for binding player stats to view
        fun bind(item: PlayerStat, position: Int, getPosition: (Int) -> Unit) {
            name.text = item.name
            score.text = item.score
            // sets up the onClickListener to the entire view
            view.setOnClickListener{
                getPosition(position)
            }
        }
    }
