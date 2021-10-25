package com.ort.gestiondetramitesmobile.models

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextInt

class Procedure(var idProcedureState: Int, private var idProcedureType: Int, var userCiudadano: User, var creationDate: Date,
                var lastModificationDate: Date,
                var licenceType: String?,var licenceCode: String?,
                var canceledReason: String?):Parcelable {

    //Atado con alambre para que ande
    var id = nextInt(1000000, 9999999)

    var selfieUrl = ""
    var selfieDniUrl = ""
    var frontDniUrl=""
    var backDniUrl = ""
    var debtFreeUrl= ""

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        TODO("userCiudadano"),
        TODO("creationDate"),
        TODO("lastModificationDate"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
        id = parcel.readInt()
        selfieUrl = parcel.readString().toString()
        selfieDniUrl = parcel.readString().toString()
        frontDniUrl = parcel.readString().toString()
        backDniUrl = parcel.readString().toString()
        debtFreeUrl = parcel.readString().toString()
    }


    fun getCurrentProcedureState(): ProcedureState{
        return getProcedureStates()[idProcedureState]
    }

    fun getCurrentProcedureType():ProcedureType{
        return getProcedureTypes()[idProcedureType]
    }

    fun isProcedureFinished():Boolean{
        return idProcedureState == getProcedureStates().size - 1
    }

    fun isProcedureCanceled():Boolean{
        return isProcedureFinished() && canceledReason?.isNotEmpty() ?: false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idProcedureState)
        parcel.writeInt(idProcedureType)
        parcel.writeString(licenceType)
        parcel.writeString(licenceCode)
        parcel.writeString(canceledReason)
        parcel.writeInt(id)
        parcel.writeString(selfieUrl)
        parcel.writeString(selfieDniUrl)
        parcel.writeString(frontDniUrl)
        parcel.writeString(backDniUrl)
        parcel.writeString(debtFreeUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Procedure> {
        override fun createFromParcel(parcel: Parcel): Procedure {
            return Procedure(parcel)
        }

        override fun newArray(size: Int): Array<Procedure?> {
            return arrayOfNulls(size)
        }
    }

    fun getFormatedLastModificationDate(): String{
        //mascara de la fecha que entra, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
        var format = SimpleDateFormat("EEE MMM dd hh:mm:ss z YYYY")
        val newDate: Date = format.parse(lastModificationDate.toString())

        //mascara de la fecha que sale, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
        format = SimpleDateFormat("dd/MM/YYYY hh:mm a")
        return format.format(newDate)
    }

    fun getFormatedCreationDate():String{
        //mascara de la fecha que entra, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
        var format = SimpleDateFormat("EEE MMM dd hh:mm:ss z YYYY")
        val newDate: Date = format.parse(creationDate.toString())

        //mascara de la fecha que sale, ver https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat#timezone
        format = SimpleDateFormat("dd/MM/YYYY hh:mm a")
        return format.format(newDate)
    }
}