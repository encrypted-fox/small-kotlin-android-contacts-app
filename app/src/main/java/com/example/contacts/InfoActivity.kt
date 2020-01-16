package com.example.contacts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val db = DatabaseController(this)

        if(intent.hasExtra("id")) {
            val id = intent.getIntExtra("id", 0)
            val contact = db.findContactById(id)
            name.setText(contact.name)
            phone.setText(contact.phone)
            email.setText(contact.email)

            btnSave.setOnClickListener {


                val contactToSave = Contact()
                contactToSave.name = name.text.toString()
                contactToSave.phone = phone.text.toString()
                contactToSave.email = email.text.toString()

                if (contactToSave.name.isNotBlank() && contactToSave.phone.isNotBlank() &&  contactToSave.email.isNotBlank()) {
                    db.updateContactById(id, contactToSave)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            btnDelete.setOnClickListener {
                db.deleteContactById(id)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        else {
            btnDelete.isEnabled = false

            btnSave.setOnClickListener {
                val name = name.text.toString()
                val phone = phone.text.toString()
                val email = email.text.toString()

                if (name.isNotBlank() && phone.isNotBlank() && phone.isNotBlank())  {
                    val db = DatabaseController(this)

                    val contact = Contact(0, name, phone, email)

                    db.addContact(contact)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}