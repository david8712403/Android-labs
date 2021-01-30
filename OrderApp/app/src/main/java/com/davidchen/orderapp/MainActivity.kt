package com.davidchen.orderapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var btChoice: Button
    lateinit var tvMeal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btChoice = findViewById(R.id.bt_order)
        tvMeal = findViewById(R.id.tv_meal)

        btChoice.setOnClickListener {
            startActivityForResult(Intent(this, ChoiceDrinkActivity::class.java), 1)
        }

        tvMeal.text = "飲料: 無\n\n" +
                "甜度: 無\n\n" +
                "冰塊: 無"
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
                tvMeal.text = "飲料: ${it.getString("drink")}\n\n" +
                        "甜度: ${it.getString("sugar")}\n\n" +
                        "甜度: ${it.getString("ice")}"
            }
        }
    }
}