package com.davidchen.contactapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MyAdapter
    private val contactList = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val btAddContact = findViewById<Button>(R.id.bt_add_contact)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView.layoutManager = linearLayoutManager
        adapter = MyAdapter(contactList)
        recyclerView.adapter = adapter

        btAddContact.setOnClickListener {
            startActivityForResult(Intent(this, AddContactActivity::class.java), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
                val name = it.getString("name") ?: return@let
                val phone = it.getString("phone") ?: return@let
                contactList.add(Contact(name, phone))
                adapter.notifyDataSetChanged()
            }
        }
    }
}

class MyAdapter(private val data: ArrayList<Contact>):
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val tvName = v.findViewById<TextView>(R.id.tv_name)
        val tvPhone = v.findViewById<TextView>(R.id.tv_phone)
        val btDelete = v.findViewById<Button>(R.id.bt_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = data[position].name
        holder.tvPhone.text = data[position].phone
        holder.btDelete.setOnClickListener {
            data.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

data class Contact (
    val name: String,
    val phone: String
)