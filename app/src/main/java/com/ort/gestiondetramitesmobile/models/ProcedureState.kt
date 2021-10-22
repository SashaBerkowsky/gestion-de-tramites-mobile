package com.ort.gestiondetramitesmobile.models

sealed class ProcedureState(val title: String, val color: String) {

    class PENDIENTE_DE_ANALISIS() : ProcedureState("Pendiente de análisis", "#909090")
    class EN_PROCESO_DE_ANALISIS() : ProcedureState("En proceso de análisis","#3D5AFE")
    class ASIGNADO_A_RESPONSABLE() : ProcedureState("Asignado a responsable","#C17817")
    class PENDIENTE_DE_RETIRO() : ProcedureState("Pendiente de retiro","#7C238C")
    class ESTADO_FINALIZADO() : ProcedureState("Finalizado","#D60909")

}

fun getProcedureStates(): MutableList<ProcedureState> {

    return mutableListOf(ProcedureState.PENDIENTE_DE_ANALISIS(),
            ProcedureState.EN_PROCESO_DE_ANALISIS(),
            ProcedureState.ASIGNADO_A_RESPONSABLE(),
            ProcedureState.PENDIENTE_DE_RETIRO(),
            ProcedureState.ESTADO_FINALIZADO())
}
