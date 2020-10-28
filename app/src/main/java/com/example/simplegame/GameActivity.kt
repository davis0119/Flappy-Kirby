package com.example.simplegame

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private var g : GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val h = displayMetrics.heightPixels
        val w = displayMetrics.widthPixels
        g = GameView(this, w.toFloat(), h.toFloat(), this)
        setContentView(g)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu -> {
                g?.mp?.stop()
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
                return true
            }
            R.id.leaderboard -> {
                g?.mp?.stop()
                val it = Intent(this, LeaderboardActivity::class.java)
                startActivity(it)
                return true
            }
            R.id.new_game -> {
                g?.mp?.stop()
                val it = Intent(this, GameActivity::class.java)
                startActivity(it)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}