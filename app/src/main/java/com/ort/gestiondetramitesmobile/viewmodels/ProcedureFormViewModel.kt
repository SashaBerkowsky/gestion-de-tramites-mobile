package com.ort.gestiondetramitesmobile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.adapters.UserRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProcedureFormViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var currentUser = MutableLiveData<User>()
    val errorMessage = MutableLiveData<String>()
    private val repository = UserRepository(RetrofitInstance)
    lateinit var procedureUser: User
    lateinit var newProcedure: Procedure
    var licenceCodes = getLicenceCodesTitles()
    var licenceTypesTitles = getLicenceTypesTitles()

    fun setCurrentUser(userId : Int){
        val responseAPI = repository.getUserById(userId)

        responseAPI.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                currentUser.value = response.body()!!
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun setProcedureUser(name: String, surname: String,  dni: String,  address: String,
                         birthdate: String){
        procedureUser = User(name, surname, dni, address, birthdate, currentUser.value?.id)
    }

    fun createProcedure( licenceType: String,licenceCode: String){
        newProcedure = Procedure(0,0, procedureUser,Date(),Date(),licenceType,licenceCode,
            "","","","","","","","",999)
    }

    fun getProcedure(): Procedure{
        return newProcedure
    }

    fun getDni(): String? {
        return currentUser.value?.dni
    }

    fun getName(): String? {
        return currentUser.value?.name
    }

    fun getSurname(): String? {
        return currentUser.value?.surname
    }

    fun getAddress(): String? {
        return currentUser.value?.address
    }

    fun getBirthdate():String? {
        return currentUser.value?.birthdate
    }

}