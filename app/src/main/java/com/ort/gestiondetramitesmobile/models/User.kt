package com.ort.gestiondetramitesmobile.models


class User(val username: String,var password: String) {
    lateinit var name: String
    lateinit var surname: String
    lateinit var dni: String
    lateinit var address: String
    lateinit var birthdate: String
}