package com.example.omemorizador.controller

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.*
import com.example.omemorizador.R
import com.example.omemorizador.model.GridTeachersAdapter
import com.example.omemorizador.model.Recorde
import com.example.omemorizador.model.Teacher
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tabuleiro.*
import android.text.InputType
import android.widget.EditText



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
    private lateinit var alerta: AlertDialog
    private var milisegundos: Long = 0
    private lateinit var nome: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabuleiro)

        abrirDialog()

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

    inner class OnClickLista : AdapterView.OnItemClickListener {
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
                            carregarFoto(this@TabuleiroActivity, escolha.foto, view)
                            posicoesAcertadas.add(numeroEscolhido)
                            posicoesAcertadas.add(position)
                            ++acertos
                            if (acertos == 8) {
                                cronometro.stop()
                                val tempo = ((SystemClock.elapsedRealtime() - milisegundos) / 1000).toInt()
                                val recorde = Recorde(nome, tempo)
                                var intent = Intent()
                                intent.putExtra("recorde", recorde)
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                        } else {
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

        }

        fun carregarFoto(context: Context, url: String, imageView: ImageView) {
            Picasso.with(context)
                .load(url)
                .into(imageView)
        }
    }

    fun abrirDialog() {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(this)

        // Set the alert dialog title
        builder.setTitle("O Memorizador")

        // Display a message on alert dialog
        builder.setMessage("Digite seu nome:")
        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Confirm") { dialog, which ->
            // Do something when user press the positive button
            this.nome = input.text.toString()
        }


        // Display a neutral button on alert dialog
        builder.setNeutralButton("Cancel") { _, _ ->
            this.nome = "Fulano"
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
}
