package com.davidchen.broadcastreceiverapp

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
    private var channel = ""
    private lateinit var thread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }

        broadcast(
            when(channel) {
                "music" -> "welcome to music channel"
                "new" -> "welcome to new channel"
                "sport" -> "welcome to sport channel"
                else -> "unknown"
            }
        )

        if (::thread.isInitialized && thread.isAlive) {
            thread.interrupt()
        }
        thread = Thread {
            try {
                Thread.sleep(2000)
                broadcast(
                    when(channel) {
                        "music" -> "play top 10 music in this month"
                        "new" -> "play some news"
                        "sport" -> "play NBA game this week"
                        else -> "unknown"
                    }
                )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        thread.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun broadcast(msg: String) {
        sendBroadcast(Intent(channel).putExtra("msg", msg))
    }
}