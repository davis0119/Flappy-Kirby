package com.example.simplegame

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import java.util.*
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    var g : GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val h = displayMetrics.heightPixels
        val w = displayMetrics.widthPixels
        g = GameView(this, w.toFloat(), h.toFloat())
        setContentView(g)
    }


}