package com.davidchen.serviceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btStart = findViewById<Button>(R.id.bt_start)

        btStart.setOnClickListener {
            startService(Intent(this, MyService::class.java))
            Toast.makeText(this, "start service", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}