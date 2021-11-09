package com.ort.gestiondetramitesmobile.daos

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.models.getProcedureStates
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DaoProcedure (procedure: Procedure){
   /* val userCiudadanoID = procedure.userCiudadanoId
    val tipoLicencia = procedure.licenceType
    val idProcedureType = procedure.getCurrentProcedureType().value*/

    val id = procedure.id
    val idUser = procedure.userCiudadano.id
    val idState = procedure.idProcedureState
    val idProcedureType = procedure.getCurrentProcedureType().value
    val userName = procedure.userCiudadano.name
    val userSurname = procedure.userCiudadano.surname
    val userDni = procedure.userCiudadano.dni
    val userAddress = procedure.userCiudadano.address
    val userBirthdate = formatBirthDateForDatabase(procedure.userCiudadano.birthdate)
    val subProcedureType = procedure.licenceType
    val licenceCode = procedure.licenceCode
    val reasonRejection = procedure.canceledReason
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun transformStrToDate(date : String) : Date {
        var simpleFormat =  DateTimeFormatter.ISO_DATE;
        val convertedDate = LocalDate.parse(date, simpleFormat)


        return Date.from(convertedDate.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }

    private fun formatBirthDateForDatabase(birthdate: String): String{
        val splitedBirthdate = birthdate.split("/")

        return splitedBirthdate[2]+"-"+splitedBirthdate[1]+"-"+splitedBirthdate[0]
    }

    private fun formatBirthdateForProcedure(birthdate: String):String{
        val splitedBirthdate = birthdate.split("-")

        return splitedBirthdate[2]+"/"+splitedBirthdate[1]+"/"+splitedBirthdate[0]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createProcedure(): Procedure{
        val user = User(userName, userSurname, userDni, userAddress, formatBirthdateForProcedure(userBirthdate), idUser)

        return Procedure(idState-1, idProcedureType - 1,user,transformStrToDate(creationDate),transformStrToDate(lastModificationDate),subProcedureType,licenceCode,reasonRejection
                         ,selfieUrl,selfieDniUrl,frontDniUrl,backDniUrl,debtFreeUrl,revisionDate,withdrawalDate,id)
    }


    fun isProcedureFinished():Boolean{
        return idState == getProcedureStates().size
    }

    override fun toString(): String{
        return (id.toString()+" "+
                idUser+" "+
                idState+" "+
                idProcedureType+" "+
                userName+" "+
                userSurname+" "+
                userDni+" "+
                userAddress+" "+
                userBirthdate+" "+
                subProcedureType+" "+
                licenceCode+" "+
                reasonRejection+" "+
                creationDate+" "+
                lastModificationDate.toString()+" "+
                selfieUrl+" "+
                selfieDniUrl+" "+
                frontDniUrl+" "+
                backDniUrl+" "+
                debtFreeUrl+" "+
                revisionDate+" "+
                withdrawalDate )
    }

}