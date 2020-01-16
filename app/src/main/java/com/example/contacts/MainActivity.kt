package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabaseController(this)
        val contactList = db.getAll()

        val adapter = ContactAdapter(this, contactList)

        listView.adapter = adapter

        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        listView.setOnItemClickListener{ parent, view, position, id ->
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra("id", contactList[position].id)
            startActivity(intent)
        }
    }

}
