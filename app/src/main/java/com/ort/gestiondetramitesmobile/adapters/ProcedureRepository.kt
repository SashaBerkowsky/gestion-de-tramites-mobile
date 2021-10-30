package com.ort.gestiondetramitesmobile.adapters

import com.ort.gestiondetramitesmobile.api.RetrofitInstance

class ProcedureRepository(private val retrofit: RetrofitInstance) {

    fun getProceduresList(idUser : String) = retrofit.apiProcedures.getProceduresList(idUser)
}