package com.davidchen.sqlitedb

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MyContentProvider : ContentProvider() {
    private lateinit var db: SQLiteDatabase
    override fun onCreate(): Boolean {
        val context = context ?: return false
        db = MyDBHelper(context).writableDatabase

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val qString = if (selection == null) null else
            "book='${selection}"

        return db.query("mTable", null, qString, null, null, null, null)
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val book = values ?: return null
        val rowId = db.insert("mTable", null, book)

        return Uri.parse("content://com.davidchen.sqlitedb/$rowId")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val name = selection ?: return 0

        return db.delete("mTable", "book='${name}'", null)
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val name = selection ?: return 0
        val price = values ?: return 0

        return db.update("mTable", price, "book='${name}'", null)
    }
}