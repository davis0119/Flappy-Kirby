package com.example.simplegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player_stat.*

class PlayerStatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_stat)

        name.text = intent.getStringExtra("name")
        score.text = intent.getStringExtra("score")
    }
}