package com.davidchen.adapterapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(context: Context, data: ArrayList<FruitItem>, private val layout: Int): ArrayAdapter<FruitItem>(context, layout, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = View.inflate(parent.context, layout, null)
        val item = getItem(position) ?: return view
        val imgPhoto = view.findViewById<ImageView>(R.id.img_photo)
        val tvMsg = view.findViewById<TextView>(R.id.tv_msg)

        imgPhoto.setImageResource(item.photo)
        tvMsg.text = if (layout == R.layout.item_vertical) {
            item.name
        } else {
            "${item.name}: $${item.price}"
        }

        return view
    }
}