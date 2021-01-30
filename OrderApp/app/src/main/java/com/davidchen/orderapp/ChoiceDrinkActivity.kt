package com.davidchen.orderapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.core.view.isNotEmpty

class ChoiceDrinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_drink)

        val btConfirm = findViewById<Button>(R.id.bt_confirm)
        val etDrink = findViewById<EditText>(R.id.et_drink)
        val rGroupSugar = findViewById<RadioGroup>(R.id.radioGroup)
        val rGroupIce = findViewById<RadioGroup>(R.id.radioGroup2)

        btConfirm.setOnClickListener {
            if (etDrink.text.isNotEmpty() &&
                    rGroupSugar.checkedRadioButtonId != -1 &&
                    rGroupIce.checkedRadioButtonId != -1) {
                val b = Bundle()
                b.putString("drink", etDrink.text.toString())
                b.putString("sugar", findViewById<RadioButton>(rGroupSugar.checkedRadioButtonId).text.toString())
                b.putString("ice", findViewById<RadioButton>(rGroupIce.checkedRadioButtonId).text.toString())
                setResult(Activity.RESULT_OK, Intent().putExtras(b))
                finish()
            }
        }
    }
}