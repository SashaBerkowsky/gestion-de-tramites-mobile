package com.ort.gestiondetramitesmobile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.adapters.ProcedureRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.Procedure
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProcedureListOldViewModel ()  : ViewModel() {

    val procedureList = MutableLiveData<List<Procedure>>()
    val errorMessage = MutableLiveData<String>()
    val oldList = mutableListOf<Procedure>()
    private val repository = ProcedureRepository(RetrofitInstance)

    fun getProceduresList() {

        val response = repository.getProceduresList()

        response.enqueue(object : Callback<List<Procedure>> {
            override fun onResponse(call: Call<List<Procedure>>, response: Response<List<Procedure>>) {
                  response.body()?.forEach {
                    if (it.isProcedureFinished()) {
                        oldList.add(it)
                    }
                }
                procedureList.postValue(oldList)
            }

            override fun onFailure(call: Call<List<Procedure>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}