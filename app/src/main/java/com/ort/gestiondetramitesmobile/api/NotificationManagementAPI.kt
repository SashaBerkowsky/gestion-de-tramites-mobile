package com.ort.gestiondetramitesmobile.api

import com.ort.gestiondetramitesmobile.models.Notification
import com.ort.gestiondetramitesmobile.models.NotificationReaded
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


//Se declaran todos los llamados a la API con este formato:
//@[POST/GET/PUT]("/endpoint")
//suspend fun nombreDeLaFuncion(@Body variable:TipoDeVariable): Response<TipoDeResponse>
interface NotificationManagementAPI {
    @GET("users/citizens/notifications")
    fun getNotificationList(@Query("idUser") idUser : String): Call<List<Notification>>
    @PUT("users/citizens/notifications")
    suspend fun putNotificationReaded(@Body notificationReaded : NotificationReaded): Response<Void>
}