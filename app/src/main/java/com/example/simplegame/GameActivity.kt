package com.example.simplegame

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    var g : GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val h = displayMetrics.heightPixels
        val w = displayMetrics.widthPixels
        g = GameView(this, w.toFloat(), h.toFloat(), this)
        setContentView(g)
//        g!!.runThread()
//        g!!.setOnClickListener { _ -> g!!.runThread()}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu -> {
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
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