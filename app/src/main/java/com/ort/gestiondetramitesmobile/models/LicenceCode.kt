package com.ort.gestiondetramitesmobile.models

//Asumo que guardo strings en base de datos
sealed class LicenceCode(var title: String){
    class A1() : LicenceCode("A1")
    class B1() : LicenceCode("B1")
    class C1() : LicenceCode("C1")
}
fun getLicenceCodes(): MutableList<LicenceCode>{
    return mutableListOf(LicenceCode.A1(),LicenceCode.B1(),LicenceCode.C1())
}

fun getLicenceCodesTitles(): MutableList<String>{
    return mutableListOf(LicenceCode.A1().title,LicenceCode.B1().title,LicenceCode.C1().title)
}

