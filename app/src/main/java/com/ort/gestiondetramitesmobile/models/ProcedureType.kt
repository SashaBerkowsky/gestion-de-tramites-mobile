package com.ort.gestiondetramitesmobile.models

sealed class ProcedureType(val title: String, val description : String, val value: Int, val neededPictures: Array<String>){

    class LICENCIA_DE_CONDUCIR() : ProcedureType("LICENCIA DE CONDUCIR",
        "Para primeras licencias, renovaciones y nuevos ejemplares",
        1, arrayOf("Foto de la persona a realizar el tramite",
            "Foto de la persona con su DNI",
            "Foto del frente del DNI",
            "Foto del dorso del DNI",
        "Foto del certificado de Libre de Deudas Multas"))
}

fun getProcedureTypes(): MutableList<ProcedureType> {
    return mutableListOf( ProcedureType.LICENCIA_DE_CONDUCIR())
}

