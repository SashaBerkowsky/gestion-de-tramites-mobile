package com.ort.gestiondetramitesmobile.viewmodels

import android.icu.text.StringPrepParseException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Tasks.await
import com.ort.gestiondetramitesmobile.adapters.ProcedureRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.api.RetrofitInstance.apiProcedures
import com.ort.gestiondetramitesmobile.daos.DaoProcedure
import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.models.ProcedureState
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import retrofit2.Call
import retrofit2.Response
import retrofit2.await
import javax.security.auth.callback.Callback

class ProcedureDetailViewModel() : ViewModel() {
    // TODO: Implement the ViewModel

    lateinit var selectedProcedure: Procedure
    var procedure = MutableLiveData<Procedure>()
    val errorMessage = MutableLiveData<String>()
    private val repository = ProcedureRepository(RetrofitInstance)

    fun setProcedure(idProcedure: Int){
        val response = repository.getProcedureById(idProcedure)
        Log.d("idProcedure", idProcedure.toString())
        response.enqueue(object : retrofit2.Callback<DaoProcedure> {
            override fun onResponse(call: Call<DaoProcedure>, response: Response<DaoProcedure>) {

                if(response.isSuccessful){
                    val dao = response.body()
                    procedure.value = dao?.createProcedure()!!
                }
            }

           override fun onFailure(call: retrofit2.Call<DaoProcedure>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

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

    fun getDescriptionText(): String?{
        return when(selectedProcedure.getCurrentProcedureState()){
            ProcedureState.ESTADO_FINALIZADO() -> getFinishedStateCanceledReason()
            ProcedureState.PENDIENTE_DE_RETIRO() -> getProcedureDates()
            else -> ""
        }
    }

    private fun getProcedureDates(): String?{
        var retDates = ""
        if(!selectedProcedure.revisionDate.isNullOrEmpty()){
            retDates += "Fecha de revision: " + selectedProcedure.revisionDate + "\n"
        } else if(!selectedProcedure.withdrawalDate.isNullOrEmpty()){
            retDates += "Fecha de revision: " + selectedProcedure.withdrawalDate
        }

        return retDates
    }

    private fun getFinishedStateCanceledReason(): String? {
        return if(selectedProcedure.isProcedureCanceled()){
            selectedProcedure.canceledReason
        } else{
            ""
        }
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