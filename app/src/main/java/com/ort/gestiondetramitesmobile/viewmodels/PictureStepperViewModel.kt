package com.ort.gestiondetramitesmobile.viewmodels

import android.graphics.Bitmap
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
    private val storage = Firebase.storage
    private val storageRef = storage.reference.child("imagesTest")

    fun createProcedure(argProcedure: Procedure){
        procedure = argProcedure
    }

    fun getCurrentProcedure(): Procedure{
        return procedure
    }

    fun uploadPicture(pictureBitmap: Bitmap, pictureNeededIdx: Int){
        val baos = ByteArrayOutputStream()
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageRef.child(Date().toString())

        var uploadTask = imageRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask {
            imageRef.downloadUrl
        }.addOnCompleteListener {
            if(it.isSuccessful){
                val downloadUri = it.result
                when(pictureNeededIdx){
                    0->procedure.selfieUrl = downloadUri.toString()
                    1->procedure.selfieDniUrl = downloadUri.toString()
                    2->procedure.frontDniUrl = downloadUri.toString()
                    3->procedure.backDniUrl = downloadUri.toString()
                    4->procedure.debtFreeUrl = downloadUri.toString()
                }
            }
        }
    }
}