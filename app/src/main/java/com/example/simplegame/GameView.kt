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
    val quesadilla = BitmapFactory.decodeResource(getResources(), R.drawable.kirby);
    val paint = Paint()

    var playerX = wide/6
    var playerY = 5*high/7

    var quesadillaX = wide
    var quesadillaY = 5*high/7
    var quesadillaSpeed = 4

    var frames = 0

    var diff = 0

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

        //canvas?.drawRect(playerX-40, playerY-40, playerX+40, playerY+40, paint)
        canvas?.drawBitmap(kirby, playerX-80, playerY-160, paint)
        paint.setColor(Color.parseColor("#f9a602"))
        canvas?.drawBitmap(quesadilla, quesadillaX-80, quesadillaY-160, paint)
        //canvas?.drawRect(quesadillaX-40, quesadillaY-40, quesadillaX+40, quesadillaY+40, paint)

        quesadillaX -= quesadillaSpeed
        if(frames > 0) {
            playerY -= diff
            diff -= 1
            frames -= 1
        }

        if (quesadillaX < 0) {
            quesadillaX = wide
            quesadillaSpeed += 2
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
        if (frames == 0) {
            frames = 61
            diff = 30
        }
    }

    fun checkCollision(): Boolean {
        if ((playerY + 40 >= quesadillaY - 40)
            && (playerY - 40 <= quesadillaY + 40)
            && (playerX + 40 >= quesadillaX - 40)
            && (playerX - 40 <= quesadillaX + 40)) {
            return true
        }
        return false
    }

}