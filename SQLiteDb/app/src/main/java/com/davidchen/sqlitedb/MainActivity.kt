package com.davidchen.sqlitedb

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = MyDBHelper(this).writableDatabase
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items
        )
        findViewById<ListView>(R.id.list_view).adapter = adapter

        val etBook = findViewById<EditText>(R.id.et_book)
        val etPrice = findViewById<EditText>(R.id.et_price)

        findViewById<Button>(R.id.bt_add).setOnClickListener {
            if (etBook.length() < 1 || etPrice.length() < 1) {
                showToast("edit text empty")
            } else {
                try {
                    db.execSQL(
                        "INSERT INTO mTable(book, price) VALUES(?,?)",
                        arrayOf(etBook.text.toString(), etPrice.text.toString())
                    )
                    showToast("insert: ${etBook.text}, price ${etPrice.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("insert fail: $e")
                }
            }
        }

        findViewById<Button>(R.id.bt_update).setOnClickListener {
            if (etBook.length() < 1 || etPrice.length() < 1) {
                showToast("edit text empty")
            } else {
                try {
                    db.execSQL("UPDATE mTable SET price = ${etPrice.text} WHERE book LIKE '${etBook.text}'")
                    showToast("update: ${etBook.text}, price ${etPrice.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("update fail: $e")
                }
            }
        }

        findViewById<Button>(R.id.bt_delete).setOnClickListener {
            if (etBook.length() < 1) {
                showToast("edit text empty")
            } else {
                try {
                    db.execSQL("DELETE FROM mTable WHERE book LIKE '${etBook.text}'")
                    showToast("delete: ${etBook.text}, price ${etPrice.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("delete fail: $e")
                }
            }
        }

        findViewById<Button>(R.id.bt_search).setOnClickListener {
            val qString = if (etBook.length() < 1) {
                "SELECT * FROM mTable"
            } else {
                "SELECT * FROM mTable WHERE book LIKE '${etBook.text}'"
            }

            val c = db.rawQuery(qString, null)

            c.moveToFirst()
            items.clear()
            showToast("${c.count} items")

            for (i in 0 until c.count) {
                items.add("書名:${c.getString(0)}\t\t\t\t 價格:${c.getInt(1)}")
                c.moveToNext() //移動到下一筆
            }
            adapter.notifyDataSetChanged()

            c.close()
        }
    }

    private fun cleanEditText() {
        findViewById<EditText>(R.id.et_book).setText("")
        findViewById<EditText>(R.id.et_price).setText("")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}