package com.example.omemorizador.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.omemorizador.R
import com.example.omemorizador.retrofit.RetrofitInitializer
import com.example.omemorizador.model.ResponseAPI
import com.example.omemorizador.model.Teacher
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var btListar: Button
    private lateinit var btJogar: Button
    private lateinit var tvStatus: TextView
    private var teachers = ArrayList<Teacher>()
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readAllTeachers()

        this.btListar = findViewById(R.id.btListar)
        this.btJogar = findViewById(R.id.btJogar)
        this.tvStatus = findViewById(R.id.tvStatus)
        this.tvStatus.text = "O app está processando os dados"

        this.btListar.setOnClickListener{ listarProfessores(it) }
        this.btJogar.setOnClickListener { abrirJogo(it) }

    }

    fun listarProfessores(v: View) {
        var intent = Intent(this, ListActivity::class.java)

        intent.putExtra("teachers", converterLista(this.teachers))
        startActivity(intent)
    }

    fun converterLista(lista: List<Teacher>): String {
        val gson = Gson()
        return gson.toJson(lista)
    }

    fun clonarListaAleatoria (lista: List<Teacher>): ArrayList<Teacher> {
        var listaClonada = ArrayList<Teacher>()
        for (t in lista)
            listaClonada.add(t.clonePublic())
        return listaClonada

    }

    fun abrirJogo(v: View) {
        val listaAleatoria: ArrayList<Teacher> = clonarListaAleatoria(this.teachers)
        listaAleatoria.shuffle()
        val slice = listaAleatoria.subList(0,8)
        slice.addAll(slice)
        slice.shuffle()

        var intent = Intent(this, TabuleiroActivity::class.java)

        intent.putExtra("teachers", converterLista(slice))
        startActivity(intent)
    }

    fun readAllTeachers() {
        do {
            val call = createCall(page)
            this.page ++
            fetchResults(call)
        }while ( this.page < 5 )
    }

    fun createCall(numberOfPage: Int): Call<ResponseAPI> {
        return RetrofitInitializer().responseAPIService().readAll(numberOfPage)

    }
    fun fetchResults (call: Call<ResponseAPI>) {
        call.enqueue(object: Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>?,
                                    response: Response<ResponseAPI>?) {
                teachers.addAll(response?.body()!!.results)
                if (teachers.size == response?.body()!!.count) {
                    tvStatus.text= "Dados processados!"
                    teachers.sort()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>?,
                                   t: Throwable?) {
                Log.i("APP_API", "Falha na requisição")
            }
        })
    }
}
