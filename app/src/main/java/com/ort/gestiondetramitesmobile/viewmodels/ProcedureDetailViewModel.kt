package com.ort.gestiondetramitesmobile.viewmodels

import android.icu.text.StringPrepParseException
import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.models.ProcedureState
import kotlinx.coroutines.selects.select

class ProcedureDetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private lateinit var selectedProcedure: Procedure

    fun setProcedure(procedure: Procedure){
        selectedProcedure = procedure
    }

    fun getStateColor(): String{
        return selectedProcedure.getCurrentProcedureState().color
    }

    fun getStateTitle(): String{
        return if(!selectedProcedure.isProcedureCanceled()){
            selectedProcedure.getCurrentProcedureState().title
        } else{
            "Rechazado"
        }
    }

    fun getDescriptionText(): String{
        return selectedProcedure.canceledReason.toString()
    }

    fun getUserName(): String{
        return selectedProcedure.userCiudadano.name
    }

    fun getUserAddress(): String{
        return selectedProcedure.userCiudadano.address
    }

    fun getUserSurname(): String{
        return selectedProcedure.userCiudadano.surname
    }

    fun getUserDni(): String{
        return selectedProcedure.userCiudadano.dni
    }

    fun getUserBirthdate(): String{
        return selectedProcedure.userCiudadano.birthdate
    }

    fun getProcedureType(): String{
        return selectedProcedure.licenceType.toString()
    }

    fun getLicenceCode(): String{
        return selectedProcedure.licenceCode.toString()
    }

    fun getProcedureName(): String {
        return selectedProcedure.getCurrentProcedureType().title
    }

    fun getSelfieUrl(): String{
        return selectedProcedure.selfieUrl.toString()
    }

    fun getSelfieDniUrl(): String{
        return selectedProcedure.selfieDniUrl.toString()
    }

    fun getFrontDniUrl(): String{
        return selectedProcedure.frontDniUrl.toString()
    }

    fun getBackDniUrl(): String{
        return selectedProcedure.backDniUrl.toString()
    }

    fun getDebtFreeUrl(): String{
        return selectedProcedure.debtFreeUrl.toString()
    }

    fun isProcedureApproved(): Boolean {
        return selectedProcedure.isProcedureApproved()
    }

    fun isProcedureCanceled(): Boolean{
        return selectedProcedure.isProcedureCanceled()
    }

    fun getProcedureState(): ProcedureState {
        return selectedProcedure.getCurrentProcedureState()
    }



}