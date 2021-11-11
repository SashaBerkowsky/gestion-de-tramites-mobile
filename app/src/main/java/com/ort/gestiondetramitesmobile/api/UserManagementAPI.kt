package com.ort.gestiondetramitesmobile.api

import com.ort.gestiondetramitesmobile.models.Address
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.models.UserToCreate
import retrofit2.Call
import retrofit2.http.*

interface UserManagementAPI {

    @GET("users/citizens")
    fun getUser(@Query("email") email : String): Call<User>

    @GET("users/citizens")
    fun getUserById(@Query("idUser") idUser : Int): Call<User>

    @POST("users/citizens")
    fun postUser(@Body user: UserToCreate): Call<User>

    @PUT("users/citizens/changeAddress")
    fun updateAddress(@Query("idUser") idUser : Int, @Body address : Address) : Call<User>
}