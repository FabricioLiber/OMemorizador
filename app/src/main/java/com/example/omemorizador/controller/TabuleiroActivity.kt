package com.example.omemorizador.controller

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.*
import com.example.omemorizador.R
import com.example.omemorizador.model.GridTeachersAdapter
import com.example.omemorizador.model.Teacher
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

class TabuleiroActivity : AppCompatActivity() {

    private lateinit var gvTabuleiro: GridView
    private lateinit var teachers: List<Teacher>
    private lateinit var escolha: Teacher
    private var numeroEscolhido: Int = -1
    private var imageViewEscolhida: ArrayList<ImageView> = ArrayList()
    private var acertos: Int = 0
    private var posicoesAcertadas: ArrayList<Int> = ArrayList()
    private val fotoDefault = "https://res.cloudinary.com/deqmrmqui/image/upload/v1558056566/icognita_rvba83.jpg"
    private lateinit var cronometro: Chronometer
    private var milisegundos: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabuleiro)

        this.gvTabuleiro = findViewById(R.id.gvTabuleiro)

        this.cronometro = Chronometer(this)
        milisegundos = SystemClock.elapsedRealtime()
        this.cronometro.start()

        val gson = Gson()
        val type = object : TypeToken<List<Teacher>>() {
        }.type
        this.teachers = gson.fromJson(intent.getStringExtra("teachers"), type)

        this.gvTabuleiro.adapter = GridTeachersAdapter(this, teachers, this)

        this.gvTabuleiro.onItemClickListener = OnClickLista()

    }

    inner class OnClickLista : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            var teacher: Teacher
            view as ImageView
            var situacao = false
            if (position != numeroEscolhido) {
                for (i in posicoesAcertadas)
                    if (position == i)
                        situacao = true
                if (!situacao) {
                    if (numeroEscolhido == -1) {
                        if (imageViewEscolhida.size > 1) {
                            for (view in imageViewEscolhida)
                                carregarFoto(this@TabuleiroActivity, fotoDefault, view)
                            imageViewEscolhida.removeAll(imageViewEscolhida)
                        }
                            imageViewEscolhida.removeAll(imageViewEscolhida)
                        escolha = teachers.get(position)
                        numeroEscolhido = position
                        imageViewEscolhida.add(view)
                        Picasso.with(this@TabuleiroActivity)
                            .load(escolha.foto)
                            .into(view)
                    } else {
                        teacher = teachers.get(position)
                        if (escolha.url.equals(teacher.url) && position != numeroEscolhido) {
                            Log.i("APP_API", "MATCH")
                            Toast.makeText(this@TabuleiroActivity, "MATCH", Toast.LENGTH_SHORT)
                            carregarFoto(this@TabuleiroActivity, escolha.foto, view)
                            posicoesAcertadas.add(numeroEscolhido)
                            posicoesAcertadas.add(position)
                            ++ acertos
                            if (acertos == 8) {
                                cronometro.stop()
                                Log.i("APP_API", (SystemClock.elapsedRealtime() - milisegundos).toString())
                                finish()
                            }
                        } else {
                            Log.i("APP_API", "NOT MATCH")
                            Toast.makeText(this@TabuleiroActivity, "NOT MATCH", Toast.LENGTH_SHORT)
                            carregarFoto(this@TabuleiroActivity, teacher.foto, view)
                            imageViewEscolhida.add(view)
                        }
                    numeroEscolhido = -1
                    }
                } else {
                    Toast.makeText(this@TabuleiroActivity, "Escolha uma carta diferente!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@TabuleiroActivity, "Escolha uma carta diferente!", Toast.LENGTH_SHORT).show()
            }
            Log.i("APP_API", acertos.toString())

        }

        fun carregarFoto(context: Context, url: String, imageView: ImageView) {
            Picasso.with(context)
                .load(url)
                .into(imageView)
        }
    }
}
