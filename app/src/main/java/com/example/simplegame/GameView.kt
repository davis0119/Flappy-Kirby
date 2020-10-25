package com.example.simplegame

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import java.util.*

class GameView(context: Context?, w: Float, h: Float) : View(context) {

    var timer : Timer
    var timehandler : Handler

    val wide = w
    val high = h
    val kirby = BitmapFactory.decodeResource(getResources(), R.drawable.kirby_gif);
    val metaknight = BitmapFactory.decodeResource(getResources(), R.drawable.metaknight);
    val paint = Paint()
    // player x and y
    var playerX = wide/6
    var playerY = 5*high/7
    // obstacle x and y
    var metaknightX = wide
    var metaknightY = 5*high/7
    var metaknightSpeed = 4
    // physics variables
    var gravity = -1
    var dy = 0

    init {
        timer = Timer()
        timehandler = Handler()
        val timetask = object : TimerTask() {
            override fun run() {
                timehandler.post(Runnable {
                    invalidate()
                    if (checkCollision()) {
                        timer.cancel()
                        val i = Intent(context, NewPlayerStat::class.java)
                        context!!.startActivity(i)
                    }
                })
            }
        }
        startTimer(timetask)
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
        dy -= gravity
        // prevent from going off screen below
        if (playerY >= 5*high/7) {
            playerY = 5 * high / 7
            dy = 0
        }
        if (metaknightX < -1800) {
            metaknightX = wide + 200
            metaknightSpeed += 2
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
        dy = -20
    }

    fun checkCollision(): Boolean {
        if ((playerY + 40 >= metaknightY - 40)
            && (playerY - 40 <= metaknightY + 40)
            && (playerX + 40 >= metaknightX - 40)
            && (playerX - 40 <= metaknightX + 40)) {
            return true
        }
        return false
    }

}