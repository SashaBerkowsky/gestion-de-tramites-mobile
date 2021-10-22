package com.ort.gestiondetramitesmobile.daos

import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.models.User
import java.util.*

class DaoProcedure (procedure: Procedure){
   /* val userCiudadanoID = procedure.userCiudadanoId
    val tipoLicencia = procedure.licenceType
    val idProcedureType = procedure.getCurrentProcedureType().value*/

    val idUserCiudadano = procedure.userCiudadanoId
    val idProcedureType = procedure.getCurrentProcedureType().value
    val userName = procedure.userCiudadano.name
    val userSurname = procedure.userCiudadano.surname
    val userDni = procedure.userCiudadano.dni
    val userAddress = procedure.userCiudadano.address
    val userBirthdate = procedure.userCiudadano.birthdate
    val subProcedureType = procedure.licenceType
    val licenceCode = procedure.licenceCode
    //TODO Add pictures URL and add it to Procedure

}