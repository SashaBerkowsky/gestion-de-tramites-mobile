package com.ort.gestiondetramitesmobile.viewmodels

import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.daos.DaoProcedure
import com.ort.gestiondetramitesmobile.models.Procedure
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

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

    //TODO Error handling
    fun sendProcedure():String{
        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)
        val procedureDao = DaoProcedure(procedure)
        scope.launch{
            val response = async{RetrofitInstance.api.postProcedure(procedureDao)}

        }

        return ""

    }

    fun getSelfieUrl(): String{
        return procedure.selfieUrl
    }

    fun getSelfieDniUrl(): String{
        return procedure.selfieDniUrl
    }

    fun getFrontDniUrl(): String{
        return procedure.frontDniUrl
    }

    fun getBackDniUrl(): String{
        return procedure.backDniUrl
    }

   fun getDebtFree(): String{
       return procedure.debtFreeUrl
   }


}