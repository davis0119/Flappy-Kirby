package com.example.simplegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
        }
        leaderboard.setOnClickListener{
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
        when (item.itemId) {
            R.id.main_menu -> {
                Toast.makeText(this, "You're already here!",
                    Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.leaderboard -> {
                val it = Intent(this, LeaderboardActivity::class.java)
                startActivity(it)
                return true
            }
            R.id.new_game -> {
                val it = Intent(this, GameActivity::class.java)
                startActivity(it)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}