package com.ort.gestiondetramitesmobile.api

import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.models.UserToCreate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserManagementAPI {
    @GET("users/citizens")
    fun getUser(@Query("email") email : String): Call<User>

    @POST("users/citizens")
    fun postUser(@Body user: UserToCreate): Call<User>
}