package com.example.simplegame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // music stuff
    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        // play da music
        mp = MediaPlayer.create(this, R.raw.kirby_main_song)
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        mp.start()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // stop the music when you go elsewhere
        game.setOnClickListener {
            mp.stop()
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
        }
        leaderboard.setOnClickListener{
            mp.stop()
            val j =  Intent(this, LeaderboardActivity::class.java)
            startActivity(j)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // stop the music when you click somewhere else on the menu
        when (item.itemId) {
            R.id.main_menu -> {
                Toast.makeText(this, "You're already here!",
                    Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.leaderboard -> {
                mp.stop()
                val it = Intent(this, LeaderboardActivity::class.java)
                startActivity(it)
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
}