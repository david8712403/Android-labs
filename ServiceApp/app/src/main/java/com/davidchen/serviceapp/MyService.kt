package com.davidchen.serviceapp

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()

        Thread {
            try {
                Thread.sleep(2000)
                val i = Intent(this, SecondActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.startActivity(i)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return Service.START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}