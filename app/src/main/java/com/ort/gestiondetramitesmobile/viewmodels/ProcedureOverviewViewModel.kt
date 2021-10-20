package com.ort.gestiondetramitesmobile.viewmodels

import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.models.Procedure

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

    //Enviar procedure a la API
    fun sendProcedure():String{
        return ""
    }
}