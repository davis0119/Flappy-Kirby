package com.example.simplegame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_game.view.*
import java.util.*

class GameView(context: Context?, w: Float, h: Float, a: Activity) : View(context) {

    var timer : Timer
    var timehandler : Handler
    val act = a
    val wide = w
    val high = h
    val kirby = BitmapFactory.decodeResource(getResources(), R.drawable.kirby_fly);
    val metaknight = BitmapFactory.decodeResource(getResources(), R.drawable.metaknight);
    val paint = Paint()
    // player x and y
    var playerX = wide/6
    var playerY = 5*high/7 + 60
    // obstacle x and y
    var metaknightX = wide
    var metaknightY = 5*high/7 + 40
    var metaknightSpeed = 7
    // physics variables
    var gravity = -1
    var dy = 0
    var metady = 0
    // player score
    var points = 0;
    init {
        timer = Timer()
        timehandler = Handler()
        val timetask = object : TimerTask() {
            override fun run() {
                timehandler.post(Runnable {
                    invalidate()
                    if (checkCollision()) {
                        // cut the cameras
                        timer.cancel()
                        val intent = Intent(context, NewPlayerStat::class.java)
                        // pass in the points scored as an intent
                        intent.putExtra("score", points)
                        context!!.startActivity(intent)
                    }
                })
            }
        }
        startTimer(timetask)
    }

    private fun runThread() {
        object : Thread() {
            val metaknight = BitmapFactory.decodeResource(getResources(), R.drawable.metaknight);
            override fun run() {
                var i = 0
                while (i++ < 1000) {
                    act.runOnUiThread {

                    }
                    Thread.sleep(2000)
                }
            }
        }.start()
    }

    fun startTimer(task: TimerTask) {
        timer = Timer()
        timer.schedule(task, 1, 10)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.setColor(Color.parseColor("#000000"));

        canvas?.drawBitmap(kirby, playerX-80, playerY-160, paint)
        paint.setColor(Color.parseColor("#f9a602"))
        canvas?.drawBitmap(metaknight, metaknightX-80, metaknightY-160, paint)
        // metaknight is going to the left faster
        metaknightX -= metaknightSpeed
        // how should the player's y coordinate be changing?
        playerY += dy
        dy -= 2 * gravity
        metaknightY += metady
        metady -= gravity
        // prevent from going off screen below
        if (playerY >= 5 * high / 7 + 60) {
            playerY = 5 * high / 7 + 60
            dy = 0
        }
        // prevent from going off screen below
        if (playerY <= 100) {
            playerY = 100F
        }
        //prevent metaknight from going off screen below
        if (metaknightY >= 5 * high / 7 + 40) {
            metaknightY = 5 * high / 7 + 40
            metady = 0
        }
        // dont let the knight go too high
        if (metaknightY <= 150) {
            metaknightY = 150F
            metady = 2
        }
        // regenerate metaknight after offscreen
        if (metaknightX < -1400) {
            points += 100
            metaknightX = wide + 100
            // metaknight might swoop in from the sky and beat u up
            metaknightY = 2500.random().toFloat()
            metaknightSpeed += 3
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {}
            MotionEvent.ACTION_UP -> jump()
            MotionEvent.ACTION_CANCEL -> {}
        }
        return true
    }

    fun jump() {
        dy = -35
        if (metady == 0) {
            metady = -10;
        } else {
            metady -= 20.random()
        }
    }

    fun Int.random(): Int {
        val x = (10..this).random()
        return x
    }

    fun checkCollision(): Boolean {
        if ((playerY + 200 >= metaknightY - 200)
            && (playerY - 200 <= metaknightY + 200)
            && (playerX + 200 >= metaknightX - 200)
            && (playerX - 200 <= metaknightX + 200)) {
            return true
        }
        return false
    }

}