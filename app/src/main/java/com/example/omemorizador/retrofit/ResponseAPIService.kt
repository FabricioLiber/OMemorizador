package com.example.omemorizador.retrofit


import com.example.omemorizador.model.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ResponseAPIService {

    @GET("professores")
    fun readAll(@Query("page") page: Int): Call<ResponseAPI>

}