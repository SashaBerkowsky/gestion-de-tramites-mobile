package com.ort.gestiondetramitesmobile.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
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

    fun getProceduresList(userId : Int) {

        val response = repository.getProceduresList(userId)

        response.enqueue(object : Callback<List<DaoProcedure>>{
            @RequiresApi(Build.VERSION_CODES.O)
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

    fun refreshProcedureList(userId: Int){
        val response = repository.getProceduresList(userId)

        response.enqueue(object : Callback<List<DaoProcedure>>{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<List<DaoProcedure>>, response: Response<List<DaoProcedure>>) {
                currentList.clear()
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


    fun getSelectedProcedure(idx: Int): Procedure? {
        return procedureList.value?.get(idx)
    }
}