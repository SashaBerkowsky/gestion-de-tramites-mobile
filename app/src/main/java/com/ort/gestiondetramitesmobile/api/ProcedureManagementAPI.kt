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
    //fun getProceduresList(): Call<List<DaoProcedure>>
    fun getProceduresList(@Query("idUser") idUser : String): Call<List<DaoProcedure>>

    @POST("/procedures")
    suspend fun postProcedure(@Body procedure: DaoProcedure): Response<ResponseBody>
}