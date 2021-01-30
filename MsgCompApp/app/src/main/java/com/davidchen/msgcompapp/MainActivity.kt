package com.davidchen.msgcompapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemArray = arrayOf(
            "dialog item 1",
            "dialog item 2",
            "dialog item 3",
            "dialog item 4",
            "dialog item 5"
        )

        val btToast = findViewById<Button>(R.id.bt_toast)
        val btCustomToast = findViewById<Button>(R.id.bt_custom)
        val btButtonDialog = findViewById<Button>(R.id.bt_button_dialog)
        val btListDialog = findViewById<Button>(R.id.bt_list_dialog)
        val btRadioButtonDialog = findViewById<Button>(R.id.bt_radio_button_dialog)

        btToast.setOnClickListener {
            Toast.makeText(this, "default toast", Toast.LENGTH_LONG).show()
        }

        btCustomToast.setOnClickListener {
            val t = Toast(this)
            t.setGravity(Gravity.TOP, 0, 50)
            t.duration = Toast.LENGTH_LONG
            t.view = layoutInflater.inflate(R.layout.custom_toast, null)
            t.show()
        }

        btButtonDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Button Dialog")
                .setMessage("message")
                .setNeutralButton("left") { _, _ ->
                    Toast.makeText(this, "left", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("middle") { _, _ ->
                    Toast.makeText(this, "middle", Toast.LENGTH_LONG).show()
                }
                .setPositiveButton("right") { _, _ ->
                    Toast.makeText(this, "right", Toast.LENGTH_LONG).show()
                }
                .show()
        }

        btListDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("List Dialog")
                .setItems(itemArray) { _, i ->
                    Toast.makeText(this, "choice item ${itemArray[i]}", Toast.LENGTH_LONG).show()
                }.show()
        }

        btRadioButtonDialog.setOnClickListener {
            var position = 0
            AlertDialog.Builder(this)
                .setTitle("Single Choice Dialog")
                .setSingleChoiceItems(itemArray, 0) {
                    _, i ->
                    position = i
                }
                .setPositiveButton("ok") { _, _ ->
                    Toast.makeText(this, "choice item ${itemArray[position]}", Toast.LENGTH_LONG).show()
                }
                .show()
        }
    }
}