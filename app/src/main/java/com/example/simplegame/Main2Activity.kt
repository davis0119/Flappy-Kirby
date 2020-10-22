package com.example.simplegame

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {

    lateinit var b: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        b = button
        b.setOnClickListener { _ -> runThread() }
    }

    private fun runThread() {
        object : Thread() {
            override fun run() {
                var i = 0
                while (i++ < 1000) {
                    runOnUiThread { b.setText("#" + i) }
                    Thread.sleep(2000)
                }
            }
        }.start()
    }
}
