package com.example.omemorizador.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import com.example.omemorizador.R
import com.example.omemorizador.model.GridTeachersAdapter
import com.example.omemorizador.model.Teacher
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TabuleiroActivity : AppCompatActivity() {

    private lateinit var gvTabuleiro: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabuleiro)

        this.gvTabuleiro = findViewById(R.id.gvTabuleiro)

        val gson = Gson()
        val type = object : TypeToken<List<Teacher>>() {
        }.type
        val teachers: List<Teacher> = gson.fromJson(intent.getStringExtra("teachers"), type)

        this.gvTabuleiro.adapter = GridTeachersAdapter(this, teachers, this)

    }
}
