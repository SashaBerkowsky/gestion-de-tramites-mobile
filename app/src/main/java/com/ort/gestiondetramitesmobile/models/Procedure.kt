package com.ort.gestiondetramitesmobile.models

import java.util.*

/*class Procedure (var id: Int, var idState: Int, var idProcedureType: Int, var idUserCiudadano: Int, var name : String,
                 var description : String, var creationDate: Date, var lastModificationDate: Date,
                 var isCanceled: Boolean) {
    val states = ProcedureState.values()

    var procedureState = states[idState]

    fun getProcedureStateTitle(): String{
        return this.procedureState.title
    }

}*/

class Procedure (var name : String, var description : String) {
}