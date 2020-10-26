package com.example.simplegame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderboardActivity : AppCompatActivity() {
    private val PLAYER_STATS: ArrayList<PlayerStat> = ArrayList<PlayerStat>()
    val ADD_NEW_PLAYER_STAT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

//        addContactData() // deleting this gets rid of all contacts
        retrieveFromPreferences() // use this instead of addContactData()

        // creates a vertical linear layout manager
        playerStat_recycler_view.layoutManager = LinearLayoutManager(this)

        //adapter with click listener
        playerStat_recycler_view.adapter = PlayerStatAdapter(this, PLAYER_STATS) {
//            // do something when clicked
//            position ->
//            val intent = Intent(this, PlayerStatActivity::class.java)
//            intent.putExtra("name", PLAYER_STATS[position].name)
//            intent.putExtra("score", PLAYER_STATS[position].score)
//            startActivity(intent)
        }

        // new contact fab button
        new_playerstat.setOnClickListener {
            val intent = Intent(this, NewPlayerStat::class.java)
//            startActivityForResult(intent, ADD_NEW_PLAYER_STAT)
            startActivity(intent)
        }
        reset.setOnClickListener {
//            clearLeaderboard()
            val i = Intent(this, ResetActivity::class.java)
            startActivity(i)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.new_game -> {
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NEW_PLAYER_STAT && resultCode ==
                    Activity.RESULT_OK && data != null) {
            // do something with the data
            val name = data.getStringExtra("name")
            val score = data.getStringExtra("score")
            if (!name.isNullOrEmpty() && !score.isNullOrEmpty()) {
                val stat = PlayerStat(name, score)
                PLAYER_STATS.add(stat)
                PLAYER_STATS.sortByDescending { stat -> stat.score.toInt() }
                playerStat_recycler_view.adapter?.notifyDataSetChanged()
                Log.d("added new stat", name)
            }
        }
    }
    // function to get stuff from preferences, similar to getting values from intents
    private fun retrieveFromPreferences() {
//        PLAYER_STATS.clear()

        val pref = this.getSharedPreferences("simplegame", Context.MODE_PRIVATE)
        val names = pref.getString("names", "")
        val score = pref.getString("score", "")
        if (names!!.isNotEmpty()) {
            val namesArr = names.split("|")
            for (name in namesArr) {
                val score = pref.getString(name + "_score", "")
                val newContact = PlayerStat(name, score!!)
                PLAYER_STATS.add(newContact)
                PLAYER_STATS.sortByDescending { stat -> stat.score.toInt() }
                if (PLAYER_STATS.size > 7) {
                    PLAYER_STATS.remove(PLAYER_STATS.get(7))
                }
            }
        }
    }
    private fun clearLeaderboard() {
        val pref = this.getSharedPreferences("simplegame", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
        finish()
    }
}