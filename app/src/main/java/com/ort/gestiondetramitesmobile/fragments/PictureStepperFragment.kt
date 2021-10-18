package com.ort.gestiondetramitesmobile.fragments

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.getExternalFilesDirs
import androidx.core.content.ContextCompat
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.PictureStepperViewModel
import androidx.core.app.ActivityCompat.startActivityForResult

import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PictureStepperFragment : Fragment() {

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_TAKE_PHOTO = 1

    lateinit var v: View

    lateinit var txtDescription: TextView
    lateinit var btnOpenCamera: Button
    lateinit var imgPictureTaken: ImageView
    lateinit var btnContinue: Button

    companion object {
        fun newInstance() = PictureStepperFragment()
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private lateinit var viewModel: PictureStepperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        viewModel = ViewModelProvider(this).get(PictureStepperViewModel::class.java)
        // TODO: Use the ViewModel
    }



    override fun onStart() {
        super.onStart()
        var pictureIdx = PictureStepperFragmentArgs.fromBundle(requireArguments()).pictureIdx
        var neededPictures =  PictureStepperFragmentArgs.fromBundle(requireArguments()).neededPictures

        txtDescription.text = neededPictures[pictureIdx]

        btnOpenCamera.setOnClickListener {
             val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
             startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }

        btnContinue.setOnClickListener {
            val needMorePictures = neededPictures.size - 1 != pictureIdx
            var action: NavDirections = if(needMorePictures){
                PictureStepperFragmentDirections.actionPictureStepperFragmentSelf(pictureIdx+1, neededPictures)
            } else{
                PictureStepperFragmentDirections.actionPictureStepperFragmentToProcedureOverviewFragment2()
            }

            findNavController().navigate(action)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
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
                val imgThumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                Log.d("width", imgThumbnail.width.toString())
                Log.d("height", imgThumbnail.height.toString())
                imgPictureTaken.setImageBitmap(imgThumbnail)
                imgPictureTaken.visibility = View.VISIBLE
            }
        }
    }

}