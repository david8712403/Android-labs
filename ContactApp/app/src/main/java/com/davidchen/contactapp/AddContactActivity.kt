package com.davidchen.contactapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        val etName = findViewById<EditText>(R.id.et_name)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val btConfirm = findViewById<Button>(R.id.bt_confirm)

        btConfirm.setOnClickListener {
            when {
                etName.text.isEmpty() -> {
                    Toast.makeText(this, "plz enter name", Toast.LENGTH_SHORT).show()
                }
                etPhone.text.isEmpty() -> {
                    Toast.makeText(this, "plz enter phone number", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val b = Bundle()
                    b.putString("name", etName.text.toString())
                    b.putString("phone", etPhone.text.toString())
                    setResult(Activity.RESULT_OK, Intent().putExtras(b))
                    finish()
                }
            }
        }
    }
}