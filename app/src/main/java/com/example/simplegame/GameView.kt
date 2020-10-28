package com.example.simplegame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.View


class GameView(context: Context?, w: Float, h: Float, a: Activity) : View(context) {
    private val cont = context
    private val act = a
    private val wide = w
    private val high = h
    private var kirby = BitmapFactory.decodeResource(getResources(), R.drawable.kirby_fly)
    private var metaknight = BitmapFactory.decodeResource(getResources(), R.drawable.metaknight)
    private val paint = Paint()
    // player x and y
    private var playerX = wide/6
    private var playerY = 5*high/7 + 60
    // obstacle x and y
    private var metaknightX = wide + 40
    private var metaknightY = 5*high/7 + 30
    private var metaknightSpeed = 7
    // physics variables
    private var gravity = -1
    private var dy = 0
    private var metady = 0
    // player score
    private var points = 0
    // music stuff
    lateinit var mp: MediaPlayer
    // are we in the sky?
    private var skyKnight = false
    // is the enemy above you?
    private var isAbove = false

    init {
        if (11.random() % 2 == 0) {
            setBackgroundResource(R.drawable.kirby_level)
        } else {
            metaknight =
                BitmapFactory.decodeResource(getResources(), R.drawable.metaknight_fly)
            setBackgroundResource(R.drawable.kirby_sky)
            metady = -5
            skyKnight = true;
        }
        runThread()
    }

    private fun runThread() {
        object : Thread() {
            override fun run() {
                // play the music!!!
                mp = MediaPlayer.create(cont, R.raw.kirby_music)
                mp.isLooping = true
                mp.setVolume(0.5f, 0.5f)
                mp.start()
                var run = true
                while (run) {
                    act.runOnUiThread {
                        invalidate()
                        // keep checking for collisions
                        if (checkCollision()) {
                            // cut the cameras
                            Thread.yield()
                            run = false
                        }
                    }
                    Thread.sleep(10)
                }
                mp.stop()
                val intent = Intent(context, NewPlayerStat::class.java)
                // pass in the points scored as an intent
                intent.putExtra("score", points)
                context!!.startActivity(intent)
            }
        }.start()
    }

    public override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.parseColor("#000000")

        canvas?.drawBitmap(kirby, playerX-80, playerY-160, paint)
        paint.color = Color.parseColor("#f9a602")
        canvas?.drawBitmap(metaknight, metaknightX-80, metaknightY-160, paint)
        // metaknight is going to the left faster
        metaknightX -= metaknightSpeed
        metaknightY += metady
        // how should the player's y coordinate be changing?
        playerY += dy
        dy -= 2 * gravity
        // check if enemy is above player
        isAbove = metaknightY <= playerY

        if (!skyKnight) {
            metady -= gravity
            // prevent metaknight from going off screen below
            if (metaknightY >= 5 * high / 7 + 40) {
                metaknightY = 5 * high / 7 + 40
                metady = 0
            }
            // dont let the knight go too high
            if (metaknightY <= 150) {
                metaknightY = 150F
                metady = 2
            }
        } else {
            // prevent metaknight from going off screen below
            if (metaknightY >= 5 * high / 8) {
                metaknightY = 5 * high / 8
                metady = 0
            }
            // dont let the knight go too high
            if (metaknightY <= 150) {
                metaknightY = 150F
                metady = 2
            }
        }
        // prevent from going off screen below
        if (playerY >= 5 * high / 7 + 60) {
            playerY = 5 * high / 7 + 60
            dy = 0
        }
        // prevent from going off screen below
        if (playerY <= 100) {
            playerY = 100F
        }

        // regenerate metaknight after offscreen
        if (metaknightX < -1400) {
            points += 100
            metaknightX = wide + 100
            // metaknight might swoop in from the sky and beat u up
            metaknightY = 2500.random().toFloat()
            metaknightSpeed += 2
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.actionMasked) {
            MotionEvent.ACTION_UP -> jump()
        }
        return true
    }

    private fun jump() {
        dy = -35
        if (metady == 0) {
            metady = -10
        } else if (isAbove) {
            // he may dive at you
            if (20.random() % 2 == 0) {
                metady += 6
            } else {
                metady -= 15.random()
            }
        } else {
            metady -= 20.random()
        }
    }

    private fun Int.random(): Int {
        return (10..this).random()
    }

    fun checkCollision(): Boolean {
        if ((playerY + 200 >= metaknightY - 200)
            && (playerY - 180 <= metaknightY + 180)
            && (playerX + 200 >= metaknightX - 200)
            && (playerX - 300 <= metaknightX + 200)) {
            return true
        }
        return false
    }

}