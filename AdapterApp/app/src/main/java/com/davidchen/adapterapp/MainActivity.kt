package com.davidchen.adapterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val gridView = findViewById<GridView>(R.id.grid_view)
        val listView = findViewById<ListView>(R.id.list_view)
        val count = ArrayList<String>()
        val item = ArrayList<FruitItem>()
        val priceRange = IntRange(10, 100)
        val array = resources.obtainTypedArray(R.array.img_list)

        for (i in 0 until array.length()) {
            val photo = array.getResourceId(i, 0)
            val name = "Fruit ${i+1}"
            val price = priceRange.random()
            count.add("${i + 1}")
            item.add(FruitItem(photo, name, price))
        }
        array.recycle()

        // spinner
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, count)

        // grid view
        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, item, R.layout.item_vertical)

        // list view
        listView.adapter = MyAdapter(this, item, R.layout.item_horizontal)

    }
}

data class FruitItem (
    val photo: Int,
    val name: String,
    val price: Int
)