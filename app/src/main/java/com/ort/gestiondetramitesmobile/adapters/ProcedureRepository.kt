package com.ort.gestiondetramitesmobile.adapters


import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.daos.DaoProcedure
import retrofit2.Call
import retrofit2.Response

class ProcedureRepository(private val retrofit: RetrofitInstance) {

    fun getProceduresList(idUser : String) = retrofit.apiProcedures.getProceduresList(idUser)

    fun getProcedureById(idProcedure: Int) : Call<DaoProcedure> = retrofit.apiProcedures.getProcedure(idProcedure)


}