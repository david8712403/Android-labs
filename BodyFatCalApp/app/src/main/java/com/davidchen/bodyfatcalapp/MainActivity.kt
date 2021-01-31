package com.davidchen.bodyfatcalapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    // main ui
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var etAge: EditText
    private lateinit var tvWeight: TextView
    private lateinit var tvBodyFat: TextView
    private lateinit var tvBmi: TextView
    private lateinit var btMale: RadioButton
    private lateinit var btCal: Button

    // progress ui
    private lateinit var llProgress: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()

        btMale.isChecked = true
        tvWeight.text = "${getString(R.string.std_weight)}\nN/A"
        tvBodyFat.text = "${getString(R.string.body_fat)}\nN/A"
        tvBmi.text = "${getString(R.string.bmi)}\nN/A"

        btCal.setOnClickListener {
            when {
                etHeight.text.isEmpty() -> showToast("plz enter height")
                etWeight.text.isEmpty() -> showToast("plz enter weight")
                etAge.text.isEmpty() -> showToast("plz enter age")
                else -> {
                    runCoroutines()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun runCoroutines() {
        tvWeight.text = "${getString(R.string.std_weight)}\nN/A"
        tvBodyFat.text = "${getString(R.string.body_fat)}\nN/A"
        tvBmi.text = "${getString(R.string.bmi)}\nN/A"


        // init progress ui
        tvProgress.text = "0%"
        progressBar.progress = 0
        llProgress.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.Main) {
            var progress = 0
            while (progress < 100) {
                delay(50)
                progressBar.progress = progress
                tvProgress.text = "$progress%"
                progress++
            }
            // update result ui
            llProgress.visibility = View.GONE
            val height = etHeight.text.toString().toDouble()
            val weight = etWeight.text.toString().toDouble()
            val age = etAge.text.toString().toDouble()
            val bmi = weight / ((height / 100).pow(2))

            val (stdWeight, bodyFat) = if (btMale.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }
            tvWeight.text = "${getString(R.string.std_weight)}\n${String.format("%.2f", stdWeight)}"
            tvBodyFat.text = "${getString(R.string.body_fat)}\n${String.format("%.2f", bodyFat)}"
            tvBmi.text = "${getString(R.string.bmi)}\n${String.format("%.2f", bmi)}"
        }
    }

    private fun initUi() {
        // main ui
        etHeight = findViewById(R.id.et_height)
        etWeight = findViewById(R.id.et_weight)
        etAge = findViewById(R.id.et_age)
        tvWeight = findViewById(R.id.tv_std_weight)
        tvBodyFat = findViewById(R.id.tv_body_fat)
        tvBmi = findViewById(R.id.tv_bmi)
        btMale = findViewById(R.id.bt_male)
        btCal = findViewById(R.id.bt_cal)

        // progress ui
        llProgress = findViewById(R.id.ll_progress)
        progressBar = findViewById(R.id.progress_bar)
        tvProgress = findViewById(R.id.tv_progress)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}