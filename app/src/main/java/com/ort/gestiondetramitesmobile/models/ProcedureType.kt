package com.ort.gestiondetramitesmobile.models

sealed class ProcedureType(val title: String, val description : String) {

    class LICENCIA_DE_CONDUCIR() : ProcedureType("LICENCIA DE CONDUCIR",
        "Para primeras licencias, renovaciones y nuevos ejemplares")
}

fun getProcedureTypes(): MutableList<ProcedureType> {

    var procedureList : MutableList<ProcedureType> = mutableListOf()
    procedureList.add(
        ProcedureType.LICENCIA_DE_CONDUCIR()
    )
    return procedureList
}