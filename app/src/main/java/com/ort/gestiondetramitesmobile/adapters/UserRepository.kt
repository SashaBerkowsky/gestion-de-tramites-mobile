package com.ort.gestiondetramitesmobile.adapters

import com.ort.gestiondetramitesmobile.api.RetrofitInstance

class UserRepository(private val retrofit: RetrofitInstance) {
    fun getUser(email : String) = retrofit.apiUser.getUser(email)
}