package com.example.omemorizador.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.omemorizador.model.ListTeachersAdapter
import com.example.omemorizador.R
import com.example.omemorizador.model.Teacher
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListActivity : AppCompatActivity() {

    private lateinit var lvTeachers: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val gson = Gson()
        val type = object : TypeToken<List<Teacher>>() {
        }.type
        val teachers: List<Teacher> = gson.fromJson(intent.getStringExtra("teachers"), type)

        this.lvTeachers = findViewById(R.id.lvTeachers)
        this.lvTeachers.adapter = ListTeachersAdapter(this, teachers, this)
    }
}
