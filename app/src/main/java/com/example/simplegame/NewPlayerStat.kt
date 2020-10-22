package com.example.simplegame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_playerstat.*
import kotlinx.android.synthetic.main.playerstat_item.*

class NewPlayerStat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_playerstat)

        save_stat.setOnClickListener {
            val returnIntent = Intent()

            returnIntent.putExtra("name", name.text.toString())
            returnIntent.putExtra("score", score.text.toString())

            setResult(Activity.RESULT_OK, returnIntent)
            // instead of using the intent, we can use shared preferences
            // first arg is name of our file
            val pref = this.getSharedPreferences("simplegame", Context.MODE_PRIVATE)
            var allNames = pref.getString("names", "")
            val name = name.text.toString()
            val editor = pref.edit()
            if (allNames!!.isEmpty()) {
                allNames += name
            } else {
                allNames += "|" + name
            }
            editor.putString("names", allNames)
            // allow us to put things into our preference file
            editor.putString(name + "_score", score.text.toString())
            editor.apply()
            finish()
        }
    }
}