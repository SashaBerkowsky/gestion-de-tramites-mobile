package com.ort.gestiondetramitesmobile.models

enum class ProcedureState(val title: String) {
    PENDIENTE_DE_ANALISIS("Pendiente de analisis"),EN_PROCESO_DE_ANALISIS("En proceso de analisis"), ASIGNADO_A_RESPONSABLE("Asignado a responsable"), PENDIENTE_DE_RETIRO("Pendiente de retiro"),ESTADO_FINALIZADO("Finalizado")
}