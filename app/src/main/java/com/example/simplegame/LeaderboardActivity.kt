package com.example.simplegame

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderboardActivity : AppCompatActivity() {
    private val STATS: ArrayList<PlayerStat> = ArrayList()
    private val ADD_NEW_STAT = 1
    // music stuff
    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        // play da music
        mp = MediaPlayer.create(this, R.raw.kirby_leaderboard)
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        mp.start()
        retrieveFromPreferences() // get persistent data

        // creates a vertical linear layout manager
        playerStat_recycler_view.layoutManager = LinearLayoutManager(this)

        //adapter with click listener
        playerStat_recycler_view.adapter = PlayerStatAdapter(this, STATS) {
            // display sole stat when clicked
            position ->
            val intent = Intent(this, PlayerStatActivity::class.java)
            intent.putExtra("name", STATS[position].name)
            intent.putExtra("score", STATS[position].score)
            startActivity(intent)
        }
        // go to reset confirmation screen
        reset.setOnClickListener {
            val builder:AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Reset Leaderboard")
            builder.setMessage("Are you sure you want to reset the leaderboard?")
            builder.setPositiveButton(
                "Resetti Spaghetti", DialogInterface.OnClickListener { dialog, id ->
                    val pref = this.getSharedPreferences("simplegame", Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.clear()
                    editor.apply()
                    mp.stop()
                    val i = Intent(this, LeaderboardActivity::class.java)
                    startActivity(i)
                    dialog.dismiss()
            })
            builder.setNegativeButton(
                "Oh, no I do not", DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this,
                        "It's all good but don't bother me again.",
                        Toast.LENGTH_SHORT).show()
                    dialog.dismiss()

            })
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu -> {
                mp.stop()
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
                return true
            }
            R.id.leaderboard -> {
                Toast.makeText(this, "You're already here!",
                    Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.new_game -> {
                mp.stop()
                val it = Intent(this, GameActivity::class.java)
                startActivity(it)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NEW_STAT && resultCode ==
                    Activity.RESULT_OK && data != null) {
            // get the data and add it to the stats
            val name = data.getStringExtra("name")
            val score = data.getStringExtra("score")
            if (!name.isNullOrEmpty() && !score.isNullOrEmpty()) {
                val stat = PlayerStat(name, score)
                // add the stat but sort it
                STATS.add(stat)
                STATS.sortByDescending { stat -> stat.score.toInt() }
                playerStat_recycler_view.adapter?.notifyDataSetChanged()
            }
        }
    }
    // function to get stuff from preferences, similar to getting values from intents
    private fun retrieveFromPreferences() {
        val pref = this.getSharedPreferences("simplegame", Context.MODE_PRIVATE)
        val names = pref.getString("names", "")
        if (names!!.isNotEmpty()) {
            val namesArr = names.split("|")
            for (name in namesArr) {
                val score = pref.getString(name + "_score", "")
                val newContact = PlayerStat(name, score!!)
                STATS.add(newContact)
                STATS.sortByDescending { stat -> stat.score.toInt() }
                // make sure the stats are not greater than 7
                if (STATS.size > 7) {
                    STATS.remove(STATS[7])
                }
            }
        }
    }
}