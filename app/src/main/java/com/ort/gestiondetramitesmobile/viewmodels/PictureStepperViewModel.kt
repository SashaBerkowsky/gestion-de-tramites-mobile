package com.ort.gestiondetramitesmobile.viewmodels

import android.graphics.Bitmap
import android.icu.text.StringPrepParseException
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ort.gestiondetramitesmobile.models.Procedure
import java.io.ByteArrayOutputStream
import java.util.*

class PictureStepperViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private lateinit var procedure : Procedure

    fun createProcedure(argProcedure: Procedure){
        procedure = argProcedure
    }

    fun getCurrentProcedure(): Procedure{
        return procedure
    }

    fun createImageName(pictureIdx: Int): String{
        val thisInstant = Date().toString()
        return when(pictureIdx){
            0->thisInstant + "_selfie"
            1->thisInstant + "_selfie_dni"
            2->thisInstant + "_front_dni"
            3->thisInstant + "_back_dni"
            4->thisInstant + "_debt_free"
            else->thisInstant + "_unknown"
        }

    }


    fun setProcedurePhoto(pictureIdx: Int, downloadUri: Uri){
        when(pictureIdx){
            0->procedure.selfieUrl = downloadUri.toString()
            1->procedure.selfieDniUrl = downloadUri.toString()
            2->procedure.frontDniUrl = downloadUri.toString()
            3->procedure.backDniUrl = downloadUri.toString()
            4->procedure.debtFreeUrl = downloadUri.toString()
        }
    }
}