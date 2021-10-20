package com.ort.gestiondetramitesmobile.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.random.Random.Default.nextInt

class Procedure(private var idProcedureState: Int, private var idProcedureType: Int, var userCiudadano: User, var creationDate: Date,
                var lastModificationDate: Date,
                var licenceType: String?,var licenceCode: String?,
                var canceledReason: String?
):Parcelable {

    //Atado con alambre para que ande
    var id = nextInt(1000000, 9999999)


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        TODO("userCiudadano"),
        TODO("creationDate"),
        TODO("lastModificationDate"),
        parcel.readString(),
        TODO("licenceCode"),
        parcel.readString()) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idProcedureState)
        parcel.writeInt(idProcedureType)
        parcel.writeString(licenceType)
        parcel.writeString(canceledReason)
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



}