package com.ort.gestiondetramitesmobile.daos

import com.ort.gestiondetramitesmobile.models.User
import java.text.SimpleDateFormat
import java.util.*

class DaoUser (user: User) {
//    "id": "11",
//    "email": "ciudadano3@ort.com.ar",
//    "name": "Sebastian",
//    "surname": "Rodriguez",
//    "dni": "32786321",
//    "address": "Juncal 1345",
//    "birthdate": "1985-01-01"
//
//    private fun formatBirthDateForDatabase(birthdate: String): String{
//        var format = SimpleDateFormat("dd/MM/YYYY")
//        val newDate: Date = format.parse(birthdate)
//
//        //mascara de la fecha que sale, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
//        format = SimpleDateFormat("YYYY-MM-dd")
//        return format.format(newDate)
//    }
//
//
//    private fun formatBirthdateForUserProfile(birthdate: String):String{
//        var format = SimpleDateFormat("YYYY-MM-dd")
//        val newDate: Date = format.parse(birthdate)
//
//        //mascara de la fecha que sale, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
//        format = SimpleDateFormat("dd/MM/YYYY")
//        return format.format(newDate)
//    }
//
//    fun createUser(): User {
//        val user = User(name, surname, dni, address, formatBirthdateForUserProfile(birthdate), idUser)
//        return user
//    }
}