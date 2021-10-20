package com.ort.gestiondetramitesmobile.api


import com.ort.gestiondetramitesmobile.models.Procedure
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//Se declaran todos los llamados a la API con este formato:
//@[POST/GET/PUT]("/endpoint")
//suspend fun nombreDeLaFuncion(@Body variable:TipoDeVariable): Response<TipoDeResponse>
interface ProcedureManagementAPI {
    @POST("/procedures")
    suspend fun postProcedure(@Body procedure: Procedure): Response<ResponseBody>
}