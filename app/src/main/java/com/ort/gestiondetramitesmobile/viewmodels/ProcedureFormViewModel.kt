package com.ort.gestiondetramitesmobile.viewmodels

import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.models.*
import java.util.*

class ProcedureFormViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private lateinit var currentUser : User
    lateinit var procedureUser: User
    lateinit var newProcedure: Procedure
    var licenceCodes = getLicenceCodesTitles()
    var licenceTypesTitles = getLicenceTypesTitles()

    fun setCurrentUser(){
        //getUserData
        currentUser = User("nombre usuario 1",
            "apellido usuario 1","123456789",
            "domicilio usuario 1","04/07/2000",
            3)
    }

    fun setProcedureUser(name: String, surname: String,  dni: String,  address: String,
                         birthdate: String){
        procedureUser = User(name, surname, dni, address, birthdate, currentUser.id)
    }

    fun createProcedure( licenceType: String,licenceCode: String){
        newProcedure = Procedure(0,0, procedureUser,Date(),Date(),licenceType,licenceCode,
            "","","","","","","","")
    }

    fun getProcedure(): Procedure{
        return newProcedure
    }

    fun getDni(): String{
        return currentUser.dni
    }

    fun getName(): String{
        return currentUser.name
    }

    fun getSurname(): String{
        return currentUser.surname
    }

    fun getAddress(): String{
        return currentUser.address
    }

    fun getBirthdate():String{
        return currentUser.birthdate
    }

    //validar datos del procedure
    fun isDataValid(): Boolean{
        return true
    }
}