package com.ort.gestiondetramitesmobile.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Se instancia un singleton de la API, no hace falta tocar nada
object RetrofitInstance {

    //URL API
    //val BASE_URL : String = "https://615b40f34a360f0017a81567.mockapi.io"
    val BASE_URL : String = "htasdasdtpasdasdassds://615b40f34asdasdasda360f0017a81567.mockapi.io"


    val api: ProcedureManagementAPI by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProcedureManagementAPI::class.java)
    }
}