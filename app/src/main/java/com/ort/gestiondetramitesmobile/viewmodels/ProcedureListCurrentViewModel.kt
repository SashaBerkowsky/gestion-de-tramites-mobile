package com.ort.gestiondetramitesmobile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.adapters.ProcedureRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.daos.DaoProcedure
import com.ort.gestiondetramitesmobile.models.Procedure
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProcedureListCurrentViewModel : ViewModel() {

    val procedureList = MutableLiveData<List<Procedure>>()
    val errorMessage = MutableLiveData<String>()
    val currentList = mutableListOf<Procedure>()
    private val repository = ProcedureRepository(RetrofitInstance)

    fun getProceduresList() {

        val response = repository.getProceduresList()

        response.enqueue(object : Callback<List<DaoProcedure>>{
            override fun onResponse(call: Call<List<DaoProcedure>>, response: Response<List<DaoProcedure>>) {
                response.body()?.forEach {
                    if (!it.isProcedureFinished()) {
                        currentList.add(it.createProcedure())
                    }
                }
                procedureList.postValue(currentList)
            }

            override fun onFailure(call: Call<List<DaoProcedure>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }
}