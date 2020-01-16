package com.example.contacts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract

class DBHelper(context: Context?)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "contactDB"
        private const val TABLE_CONTACTS = "contacts"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"

        const val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PHONE + " TEXT," +
                COLUMN_EMAIL + " TEXT)")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addContact(contact: Contact) {
        val values = ContentValues()
        values.put(COLUMN_NAME, contact.name)
        values.put(COLUMN_PHONE, contact.phone)
        values.put(COLUMN_EMAIL, contact.email)

        val db = this.writableDatabase

        db.insert(TABLE_CONTACTS, null, values)
        db.close()
    }

    fun getAll() : ArrayList<Contact> {
        val contactList = ArrayList<Contact>()
        val query = "SELECT * FROM $TABLE_CONTACTS"

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phone = cursor.getString(2)
                val email = cursor.getString(3)
                val contact = Contact(id , name, phone, email)
                contactList.add(contact)
            } while (cursor.moveToNext())

            cursor.close()
        }

        db.close()
        return contactList
    }

    fun findContactById(contactId: Int): Contact {
        val person = Contact()

        val query = "SELECT * FROM $TABLE_CONTACTS WHERE $COLUMN_ID = \"$contactId\""

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            person.id = cursor.getInt(0)
            person.name = cursor.getString(1)
            person.phone = cursor.getString(2)
            person.email = cursor.getString(3)
            cursor.close()
        }
        db.close()
        return person
    }

    fun updateContactById(contactId: Int, contact: Contact) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, contact.name)
        values.put(COLUMN_PHONE, contact.phone)
        values.put(COLUMN_EMAIL, contact.email)

        db.update(TABLE_CONTACTS, values, "$COLUMN_ID = $contactId", null)
        db.close()
    }

    fun deleteContactById(contactId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CONTACTS, "$COLUMN_ID = $contactId", null)
    }
}