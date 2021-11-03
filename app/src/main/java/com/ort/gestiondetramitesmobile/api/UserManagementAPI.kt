package com.ort.gestiondetramitesmobile.api

import com.ort.gestiondetramitesmobile.daos.DaoUser
import com.ort.gestiondetramitesmobile.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserManagementAPI {
    @GET("users/citizens")
    fun getUser(@Query("email") email : String): Call<User>

    @POST("users/citizens")
    suspend fun postUser(@Body user: User): Response<ResponseBody>
}