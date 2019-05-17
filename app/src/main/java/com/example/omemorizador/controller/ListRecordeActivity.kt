package com.example.omemorizador.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.omemorizador.R
import com.example.omemorizador.model.Recorde
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListRecordeActivity : AppCompatActivity() {

    private lateinit var lvRecordes: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_recorde)

        val gson = Gson()
        val type = object : TypeToken<List<Recorde>>() {
        }.type
        val recordes: List<Recorde> = gson.fromJson(intent.getStringExtra("Recordes"), type)

        this.lvRecordes = findViewById(R.id.lvRecorde)
        this.lvRecordes.adapter = 

    }
}
