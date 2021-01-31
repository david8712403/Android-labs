package com.davidchen.tortoisehareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // progress value
    private var progressThread = 0      // hare
    private var progressCoroutines = 0  // tortoise

    // UI
    private lateinit var btStart: Button
    private lateinit var sbThread: SeekBar
    private lateinit var sbCoroutines: SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()

        btStart.setOnClickListener {
            btStart.isEnabled = false
            progressCoroutines = 0
            progressThread = 0
            sbCoroutines.progress = 0
            sbThread.progress = 0
            runHare()
            runTortoise()
        }
    }

    private fun initUi() {
        btStart = findViewById(R.id.bt_start)
        sbThread = findViewById(R.id.sb_thread)
        sbCoroutines = findViewById(R.id.sb_coroutines)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == 1) {
            sbThread.progress = progressThread
        }
        if (progressThread >= 100 && progressCoroutines < 100) {
            showToast("hare win (thread)")
            btStart.isEnabled = true
        }
        true
    }

    private fun runHare() {
        Thread {
            val sleepProbability = arrayOf(true, true, false)
            while (progressThread < 100 && progressCoroutines < 100) {
                try {
                    Thread.sleep(100)
                    if (sleepProbability.random()) {
                        Thread.sleep(300)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressThread += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private fun runTortoise() {
        GlobalScope.launch(Dispatchers.Main) {
            while (progressThread < 100 && progressCoroutines < 100) {
                delay(100)
                progressCoroutines += 1
                sbCoroutines.progress = progressCoroutines
                if (progressCoroutines >= 100 && progressThread < 100) {
                    showToast("tortoise win (coroutines)")
                    btStart.isEnabled = true
                }
            }
        }
    }
}