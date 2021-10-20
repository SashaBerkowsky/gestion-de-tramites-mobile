package com.ort.gestiondetramitesmobile.viewmodels

import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.models.Procedure

class PictureStepperViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private lateinit var procedure : Procedure

    fun createProcedure(argProcedure: Procedure){
        procedure = argProcedure
    }

    fun getCurrentProcedure(): Procedure{
        return procedure
    }
}