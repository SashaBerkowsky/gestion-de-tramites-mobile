package com.ort.gestiondetramitesmobile.api

import com.ort.gestiondetramitesmobile.daos.DaoProcedure
import com.ort.gestiondetramitesmobile.models.Notification
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


//Se declaran todos los llamados a la API con este formato:
//@[POST/GET/PUT]("/endpoint")
//suspend fun nombreDeLaFuncion(@Body variable:TipoDeVariable): Response<TipoDeResponse>
interface NotificationManagementAPI {
    @GET("users/citizens/notifications")
    fun getNotificationList(@Query("idUser") idUser : String): Call<List<Notification>>
}