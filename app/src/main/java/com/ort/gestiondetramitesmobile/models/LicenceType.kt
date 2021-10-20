package com.ort.gestiondetramitesmobile.models

//Asumo que guardo strings en base de datos
sealed class LicenceType(var title: String){
    class PRIMERA_LICENCIA() : LicenceType("Primera Licencia")
    class RENOVACION() : LicenceType("Renovacion")
}

fun getLicenceTypes(): MutableList<LicenceType>{
    return mutableListOf(LicenceType.PRIMERA_LICENCIA(),LicenceType.RENOVACION())
}

fun getLicenceTypesTitles(): MutableList<String>{
    return mutableListOf(LicenceType.PRIMERA_LICENCIA().title,LicenceType.RENOVACION().title)
}
