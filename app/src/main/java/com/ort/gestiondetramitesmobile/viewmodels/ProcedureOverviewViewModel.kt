package com.ort.gestiondetramitesmobile.viewmodels

import android.icu.text.StringPrepParseException
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.daos.DaoProcedure
import com.ort.gestiondetramitesmobile.models.Procedure
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import com.google.gson.Gson
import org.json.JSONObject







class ProcedureOverviewViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private lateinit var procedure : Procedure

    fun createProcedure(argProcedure:Procedure){
        procedure = argProcedure
    }

    fun getProcedure():Procedure{
        return procedure
    }

    fun getDni(): String{
        return procedure.userCiudadano.dni
    }

    fun getName(): String{
        return  procedure.userCiudadano.name
    }

    fun getSurname(): String{
        return  procedure.userCiudadano.surname
    }

    fun getAddress(): String{
        return  procedure.userCiudadano.address
    }

    fun getBirthdate():String{
        return  procedure.userCiudadano.birthdate
    }

    fun getLicenceCode():String{
        return procedure.licenceCode.toString()
    }

    fun getLicenceType():String{
        return procedure.licenceType.toString()
    }

    fun getImagesAsArray(): Array<String>{
        return procedure.getImagesAsArray()
    }

    //TODO Error handling
    fun sendProcedure(onResult:(response:Int)->Unit):String{
        var retString = ""
        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)
        val procedureDao = DaoProcedure(procedure)

        val call = RetrofitInstance.apiProcedures.postProcedure(procedureDao)
        call.enqueue(object:Callback<DaoProcedure>{
            override fun onResponse(call: Call<DaoProcedure>, response: Response<DaoProcedure>) {
                retString = response.errorBody().toString()
                onResult(response.code())

            }

            override fun onFailure(call: Call<DaoProcedure>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


        return retString
    }

}