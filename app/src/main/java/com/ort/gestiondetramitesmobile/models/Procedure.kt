package com.ort.gestiondetramitesmobile.models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.sql.Date.valueOf
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class Procedure(var idProcedureState: Int, private var idProcedureType: Int, var userCiudadano: User, var creationDate: Date,
                var lastModificationDate: Date,
                var licenceType: String?,var licenceCode: String?,
                var canceledReason: String?, var selfieUrl: String?, var selfieDniUrl: String?,
                var frontDniUrl: String?, var backDniUrl: String?, var debtFreeUrl: String?,
                var revisionDate: String?, var withdrawalDate: String?, var id: Int):Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        TODO("userCiudadano"),
        TODO("creationDate"),
        TODO("lastModificationDate"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()) {
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

    fun isProcedureApproved(): Boolean{
        return isProcedureFinished() && !isProcedureCanceled()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormatedCreationDate():String{
        val datetime = creationDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();




        return datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idProcedureState)
        parcel.writeInt(idProcedureType)
        parcel.writeString(licenceType)
        parcel.writeString(licenceCode)
        parcel.writeString(canceledReason)
        parcel.writeString(selfieUrl)
        parcel.writeString(selfieDniUrl)
        parcel.writeString(frontDniUrl)
        parcel.writeString(backDniUrl)
        parcel.writeString(debtFreeUrl)
        parcel.writeString(revisionDate)
        parcel.writeString(withdrawalDate)
        parcel.writeInt(id)
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
}