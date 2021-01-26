package com.davidchen.moraapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.et_name)
        val tvText = findViewById<TextView>(R.id.tv_text)
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvWinner = findViewById<TextView>(R.id.tv_winner)
        val tvMMora = findViewById<TextView>(R.id.tv_mmora)
        val tvCMora = findViewById<TextView>(R.id.tv_cmora)
        val rbtScissor = findViewById<RadioButton>(R.id.rbt_scissor)
        val rbtStone = findViewById<RadioButton>(R.id.rbt_stone)
        val rbtPaper = findViewById<RadioButton>(R.id.rbt_paper)
        val btMora = findViewById<Button>(R.id.bt_mora)

        btMora.setOnClickListener {
            if (etName.length() < 1) {
                tvText.text = getString(R.string.play_hint)
            }else {
                tvName.text = "${getString(R.string.name)}\n${etName.text}"
                tvMMora.text = "${getString(R.string.mmora)}\n${
                    when {
                        rbtScissor.isChecked -> {
                            getString(R.string.scissor)
                        }
                        rbtStone.isChecked -> {
                            getString(R.string.stone)
                        }
                        else -> {
                            getString(R.string.paper)
                        }
                    }
                }"

                val cmora = (Math.random()*3).toInt()
                tvCMora.text = "${getString(R.string.cmora)}\n${
                    when(cmora) {
                        0 -> {
                            getString(R.string.scissor) 
                        }
                        1 -> {
                            getString(R.string.stone)
                        }
                        else -> {
                            getString(R.string.paper)
                        }
                    }
                }"

                when {
                    (rbtScissor.isChecked && cmora == 2) || (rbtStone.isChecked && cmora == 0) || (rbtPaper.isChecked && cmora == 1) -> {
                        tvWinner.text = "${getString(R.string.winner)}\n${etName.text}"
                        tvText.text = getText(R.string.result_win)
                    }
                    (rbtScissor.isChecked && cmora == 1) || (rbtStone.isChecked && cmora == 2) || (rbtPaper.isChecked && cmora == 0) -> {
                        tvWinner.text = "${getString(R.string.winner)}\n電腦"
                        tvText.text = getText(R.string.result_loss)
                    }
                    else -> {
                        tvWinner.text = "${getString(R.string.winner)}\n${getString(R.string.draw)}"
                        tvText.text = getText(R.string.result_draw)
                    }
                }
            }
        }
    }
}