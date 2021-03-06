package com.ort.gestiondetramitesmobile.api

import com.ort.gestiondetramitesmobile.daos.DaoProcedure
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//Se declaran todos los llamados a la API con este formato:
//@[POST/GET/PUT]("/endpoint")
//suspend fun nombreDeLaFuncion(@Body variable:TipoDeVariable): Response<TipoDeResponse>
interface ProcedureManagementAPI {

    @GET("procedures/user")
    fun getProceduresList(@Query("idUser") idUser : Int): Call<List<DaoProcedure>>

    @GET("procedures")
    fun getProcedure(@Query("idProcedure") idProcedure: Int): Call<DaoProcedure>

    @POST("procedures")
    fun postProcedure(@Body procedure: DaoProcedure): Call<DaoProcedure>
}