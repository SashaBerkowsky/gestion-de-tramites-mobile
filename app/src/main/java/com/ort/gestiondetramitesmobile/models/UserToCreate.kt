package com.ort.gestiondetramitesmobile.models


class UserToCreate ( var name: String,
                     var surname: String, var dni: String, var address: String,
                      inBirthdate: String, var email: String) {
    var birthdate = formatDateForAPI(inBirthdate)

    private fun formatDateForAPI(birthdate: String):String{
        val splitedBirthdate = birthdate.split("/")

        return splitedBirthdate!![2]+"-"+splitedBirthdate[1]+"-"+splitedBirthdate[0]

    }

}