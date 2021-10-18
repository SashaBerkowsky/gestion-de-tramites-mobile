package com.ort.gestiondetramitesmobile.models

class TramiteLicenciaConducir(user:User, name:String, entregado:String, state:String)
                                : Tramite(user, name, entregado, state) {

    lateinit var tipoLicencia: String
    lateinit var foto1URL:String
    lateinit var foto2URL:String
    lateinit var foto3URL:String
    lateinit var foto4URL:String
    lateinit var foto5URL:String
    lateinit var foto6URL:String
}