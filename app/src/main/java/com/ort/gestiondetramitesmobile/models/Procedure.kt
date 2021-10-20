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


    private val procedureStates = getProcedureStates()
    private val procedureTypes = getProcedureTypes()

    var isFinished = idProcedureState == procedureStates.size - 1
    var isCanceled = isFinished && canceledReason?.isNotEmpty() ?: false

    //Atado con alambre para que ande
    var id = nextInt(1000000, 9999999)

    var procedureState = procedureStates[idProcedureState]
    var procedureType = procedureTypes[idProcedureType]

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        TODO("userCiudadano"),
        TODO("creationDate"),
        TODO("lastModificationDate"),
        parcel.readString(),
        TODO("licenceCode"),
        parcel.readString()) {
        isFinished = parcel.readByte() != 0.toByte()
        isCanceled = parcel.readByte() != 0.toByte()
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idProcedureState)
        parcel.writeInt(idProcedureType)
        parcel.writeString(licenceType)
        parcel.writeString(canceledReason)
        parcel.writeByte(if (isFinished) 1 else 0)
        parcel.writeByte(if (isCanceled) 1 else 0)
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