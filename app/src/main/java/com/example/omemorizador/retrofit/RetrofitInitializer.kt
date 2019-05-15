package com.example.omemorizador.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://apiprofessoresifpbtsi.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun teacherService() = retrofit.create(TeacherService::class.java)
    fun responseAPIService() = retrofit.create(ResponseAPIService::class.java)

}