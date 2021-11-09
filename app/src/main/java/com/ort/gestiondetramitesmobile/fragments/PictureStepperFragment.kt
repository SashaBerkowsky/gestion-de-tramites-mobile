package com.ort.gestiondetramitesmobile.fragments

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.getExternalFilesDirs
import androidx.core.content.ContextCompat
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.PictureStepperViewModel
import androidx.core.app.ActivityCompat.startActivityForResult

import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureFormViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.drawable.BitmapDrawable
import android.opengl.Visibility
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStorageDirectory
import android.view.Window
import android.widget.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


class PictureStepperFragment : Fragment() {

    lateinit var v: View

    private val storage = Firebase.storage
    private val storageRef = storage.reference.child("imagesTest")

    private val viewModel: PictureStepperViewModel by viewModels()

    private val FILE_NAME: String = Date().toString() + " Photo.jpg"
    private lateinit var photoFile: File

    lateinit var txtDescription: TextView
    lateinit var btnOpenCamera: Button
    lateinit var imgPictureTaken: ImageView
    lateinit var btnContinue: Button

    companion object {
        fun newInstance() = PictureStepperFragment()
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        v= inflater.inflate(R.layout.picture_stepper_fragment, container, false)

        txtDescription = v.findViewById(R.id.txtDataDescription)
        btnOpenCamera = v.findViewById(R.id.btnOpenCamera)
        imgPictureTaken = v.findViewById(R.id.imgPictureTaken)
        btnContinue = v.findViewById(R.id.btnContinue)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }



    override fun onStart() {
        super.onStart()
        var pictureIdx = PictureStepperFragmentArgs.fromBundle(requireArguments()).pictureIdx
        var neededPictures =  PictureStepperFragmentArgs.fromBundle(requireArguments()).neededPictures
        var currentProcedure = PictureStepperFragmentArgs.fromBundle(requireArguments()).procedure
        viewModel.createProcedure(currentProcedure)

        txtDescription.text = neededPictures[pictureIdx]

        btnOpenCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)
           // intent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
            val fileProvider = FileProvider.getUriForFile(requireContext(), "com.ort.gestiondetramitesmobile", photoFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }

        btnContinue.setOnClickListener {
            if (imgPictureTaken.visibility == View.VISIBLE) {
                uploadPicture((imgPictureTaken.drawable as BitmapDrawable).bitmap,pictureIdx, neededPictures)
            } else {
                Toast.makeText(
                    this.context,
                    "Debe tomar una foto",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    private fun uploadPicture(pictureBitmap: Bitmap, pictureNeededIdx: Int, neededPictures:Array<String>){
        val needMorePictures = neededPictures.size - 1 != pictureNeededIdx
        val baos = ByteArrayOutputStream()
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageRef.child(viewModel.createImageName(pictureNeededIdx))

        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)

        dialog.show()

        var uploadTask = imageRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask {
            imageRef.downloadUrl

        }.addOnCompleteListener {
            if(it.isSuccessful){
                val downloadUri = it.result

                viewModel.setProcedurePhoto(pictureNeededIdx, downloadUri)

                dialog.dismiss()

                var action : NavDirections = if(needMorePictures){
                    PictureStepperFragmentDirections.actionPictureStepperFragmentSelf(pictureNeededIdx+1, neededPictures, viewModel.getCurrentProcedure())
                } else{
                    PictureStepperFragmentDirections.actionPictureStepperFragmentToProcedureOverviewFragment2(viewModel.getCurrentProcedure())
                }
                findNavController().navigate(action)

                //else throw toast diciendo error subiendo foto
            } else{
                dialog.dismiss()
            }

        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else{
                //mostrar toast de permiso de camara
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_REQUEST_CODE){
            //    val imgThumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
                Log.d("width", takenImage.width.toString())
                Log.d("height", takenImage.height.toString())
                imgPictureTaken.setImageBitmap(takenImage)
                imgPictureTaken.visibility = View.VISIBLE
            }
        }
    }

}