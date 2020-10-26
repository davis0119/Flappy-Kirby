package com.example.simplegame

import android.graphics.BitmapFactory
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
        object : Thread() {
            override fun run() {
                var i = 0
                while (i++ < 1000) {
                    runOnUiThread {
                        val metaknight =
                            BitmapFactory.decodeResource(getResources(), R.drawable.metaknight);
                        // obstacle x and y
                        var metaknightX = w
                        var metaknightY = 5*h/7
                        var metaknightSpeed = 7
                    }
                    Thread.sleep(2000)
                }
            }
        }.start()
        g = GameView(this, w.toFloat(), h.toFloat())
        setContentView(g)
    }

    private fun runThread() {
        object : Thread() {
            val metaknight = BitmapFactory.decodeResource(getResources(), R.drawable.metaknight);
            override fun run() {
                var i = 0
                while (i++ < 1000) {
                    runOnUiThread {

                    }
                    Thread.sleep(2000)
                }
            }
        }.start()
    }


}