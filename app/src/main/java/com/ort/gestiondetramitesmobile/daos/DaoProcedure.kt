package com.ort.gestiondetramitesmobile.daos

import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.models.getProcedureStates
import java.text.SimpleDateFormat
import java.util.*

class DaoProcedure (procedure: Procedure){
   /* val userCiudadanoID = procedure.userCiudadanoId
    val tipoLicencia = procedure.licenceType
    val idProcedureType = procedure.getCurrentProcedureType().value*/

    val idUserCiudadano = procedure.userCiudadano.userID
    val idProcedureState = procedure.idProcedureState
    val idProcedureType = procedure.getCurrentProcedureType().value
    val userName = procedure.userCiudadano.name
    val userSurname = procedure.userCiudadano.surname
    val userDni = procedure.userCiudadano.dni
    val userAddress = procedure.userCiudadano.address
    val userBirthdate = formatBirthDateForDatabase(procedure.userCiudadano.birthdate)
    val subProcedureType = procedure.licenceType
    val licenceCode = procedure.licenceCode
    val canceledReason = procedure.canceledReason
    val creationDate = formatDateForDatabase(procedure.creationDate)
    val lastModificationDate = formatDateForDatabase(procedure.lastModificationDate)
    //TODO Add pictures URL and add it to Procedure
    val selfieUrl = procedure.selfieUrl
    val selfieDniUrl = procedure.selfieDniUrl
    val frontDniUrl= procedure.frontDniUrl
    val backDniUrl = procedure.backDniUrl
    val debtFreeUrl= procedure.debtFreeUrl
    val revisionDate = procedure.revisionDate
    val withdrawalDate = procedure.withdrawalDate

    private fun formatDateForDatabase(date: Date):String{
        val format = SimpleDateFormat("EEE MMM dd hh:mm:ss z YYYY")

        return format.format(date)
    }

    private fun formatDateForProcedure(date: String): Date{
        //mascara  de la fecha que entra, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
        var format = SimpleDateFormat("EEE MMM dd hh:mm:ss z YYYY")

       return format.parse(date)
    }

    private fun formatBirthDateForDatabase(birthdate: String): String{
        var format = SimpleDateFormat("dd/MM/YYYY")
        val newDate: Date = format.parse(birthdate)

        //mascara de la fecha que sale, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
        format = SimpleDateFormat("YYYY-MM-dd")
        return format.format(newDate)
    }

    private fun formatBirthdateForProcedure(birthdate: String):String{
        var format = SimpleDateFormat("YYYY-MM-dd")
        val newDate: Date = format.parse(birthdate)

        //mascara de la fecha que sale, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
        format = SimpleDateFormat("dd/MM/YYYY")
        return format.format(newDate)
    }

    fun createProcedure(): Procedure{
        val user = User(userName, userSurname, userDni, userAddress, formatBirthdateForProcedure(userBirthdate), idUserCiudadano)
        return Procedure(idProcedureState, idProcedureType - 1,user,
            formatDateForProcedure(creationDate),formatDateForProcedure(lastModificationDate)
            ,subProcedureType,licenceCode, canceledReason
            ,selfieUrl,selfieDniUrl,frontDniUrl,backDniUrl,debtFreeUrl,revisionDate,withdrawalDate)

    }

    fun isProcedureFinished():Boolean{
        return idProcedureState == getProcedureStates().size - 1
    }

}