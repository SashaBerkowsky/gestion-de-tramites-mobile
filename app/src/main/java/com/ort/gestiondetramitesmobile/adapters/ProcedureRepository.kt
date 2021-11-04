package com.ort.gestiondetramitesmobile.adapters


import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.daos.DaoProcedure
import retrofit2.Call

class ProcedureRepository(private val retrofit: RetrofitInstance) {

    fun getProceduresList(idUser : Int) = retrofit.apiProcedures.getProceduresList(idUser)

    fun getProcedureById(idProcedure: Int) : Call<DaoProcedure> = retrofit.apiProcedures.getProcedure(idProcedure)

}