package com.example.omemorizador.retrofit


import com.example.omemorizador.model.Teacher
import retrofit2.Call
import retrofit2.http.GET

interface TeacherService {

    @GET("professores")
    fun readAll(): Call<List<Teacher>>

}