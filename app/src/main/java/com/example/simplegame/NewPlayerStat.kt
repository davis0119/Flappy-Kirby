package com.example.simplegame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_playerstat.*

class NewPlayerStat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_playerstat)
        score.text = intent.getIntExtra("score", 0).toString()

        save_stat.setOnClickListener {
            if (name.text.toString() == "") {
                Toast.makeText(this@NewPlayerStat,
                    "You must enter a name!",
                    Toast.LENGTH_SHORT).show()
            } else if (name.text.length > 12) {
                Toast.makeText(this@NewPlayerStat,
                    "Your name is a bit long...",
                    Toast.LENGTH_SHORT).show()
            } else {
                val returnIntent = Intent()

                returnIntent.putExtra("name", name.text.toString())
                setResult(Activity.RESULT_OK, returnIntent)
                // instead of using the intent, we can use shared preferences
                // first arg is name of our file
                val pref = this.getSharedPreferences("simplegame", Context.MODE_PRIVATE)
                val allNames = pref.getString("names", "")
                val name = name.text.toString() + " "
                val editor = pref.edit()
                if (allNames!!.isEmpty()) {
                    editor.putString("names", name)
                } else {
                    editor.putString("names", allNames + "|" + name)
                }
                // allow us to put things into our preference file
                editor.putString(name + "_score", score.text.toString())
                editor.apply()
                val i = Intent(this, LeaderboardActivity::class.java)
                startActivity(i)
            }
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