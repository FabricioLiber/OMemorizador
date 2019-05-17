package com.example.oagendador

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class JogoDBHelper (context: Context):
    SQLiteOpenHelper(context, "banco", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table recordes (" +
                "id integer primary key autoincrement, " +
                "nome text, " +
                "tempo integer" +
                ")"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}