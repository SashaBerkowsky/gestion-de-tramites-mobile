package com.ort.gestiondetramitesmobile.adapters

import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.models.UserToCreate

class UserRepository(private val retrofit: RetrofitInstance) {
    fun getUser(email : String) = retrofit.apiUser.getUser(email)
    fun postUser(user : UserToCreate) = retrofit.apiUser.postUser(user)
    fun getUserById(idUser : Int) = retrofit.apiUser.getUserById(idUser)
}