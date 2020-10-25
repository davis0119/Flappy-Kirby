package com.example.simplegame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reset.*

class ResetActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        confirm.setOnClickListener {
            if (confirmation.text.toString().equals("CONFIRM")) {
                val pref = this.getSharedPreferences("simplegame", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.clear()
                editor.apply()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this@ResetActivity,
                    "You must type CONFIRM to reset the leaderboard",
                    Toast.LENGTH_SHORT).show()
            }
        }
        leaderboard.setOnClickListener {
            val j =  Intent(this, LeaderboardActivity::class.java)
            startActivity(j)
        }
    }
}