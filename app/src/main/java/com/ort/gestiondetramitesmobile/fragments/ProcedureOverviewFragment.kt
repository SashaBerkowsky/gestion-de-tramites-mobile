package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.adapters.ImageListAdapter
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureOverviewViewModel
import kotlinx.coroutines.*

class ProcedureOverviewFragment : Fragment() {

    private val viewModel: ProcedureOverviewViewModel by viewModels()
    private lateinit var v : View

    private lateinit var edtName: EditText
    private lateinit var edtSurname: EditText
    private lateinit var edtDni: EditText
    private lateinit var edtAddress: EditText
    private lateinit var edtBirthdate: EditText
    private lateinit var edtLicenceType : AutoCompleteTextView
    private lateinit var edtLicenceCode : AutoCompleteTextView
    private lateinit var btnSendProcedure : Button
    private lateinit var imagesRec: RecyclerView
    private lateinit var dialog : Dialog

    companion object {
        fun newInstance() = ProcedureOverviewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var procedure = ProcedureOverviewFragmentArgs.fromBundle(requireArguments()).procedure

        v =  inflater.inflate(R.layout.procedure_overview_fragment, container, false)

        viewModel.createProcedure(procedure)

        edtDni = v.findViewById(R.id.edtDniNumber)
        edtName = v.findViewById(R.id.edtName)
        edtSurname = v.findViewById(R.id.edtSurname)
        edtAddress = v.findViewById(R.id.edtAddress)
        edtBirthdate = v.findViewById(R.id.edtBirthdate)
        edtLicenceType = v.findViewById(R.id.edtProcedureType)
        edtLicenceCode = v.findViewById(R.id.edtLicenceType)
        btnSendProcedure = v.findViewById(R.id.btnSendProcedure)
        imagesRec = v.findViewById(R.id.recImages)

        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)

        return v
    }

    override fun onStart() {
        super.onStart()

        val adapter = ImageListAdapter(viewModel.getImagesAsArray(), requireContext())
        imagesRec.adapter = adapter
        imagesRec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        edtDni.setHint(viewModel.getDni())
        edtName.setHint(viewModel.getName())
        edtSurname.setHint(viewModel.getSurname())
        edtAddress.setHint(viewModel.getAddress())
        edtBirthdate.setHint(viewModel.getBirthdate())
        edtLicenceType.setHint(viewModel.getLicenceType())
        edtLicenceCode.setHint(viewModel.getLicenceCode())

        btnSendProcedure.setOnClickListener {
            dialog.show()

            viewModel.sendProcedure(){
                if(it != 400){
                    val action = ProcedureOverviewFragmentDirections.actionProcedureOverviewFragment2ToProcedureSendedFragment2()
                    findNavController().navigate(action)

                } else{
                    showErrorDialog("Tramite inválido")
                }
                dialog.dismiss()
            }

        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }




    private fun showErrorDialog(errorMsg: String){
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.state_dialog)

        dialog.setOnDismissListener {
            val action = ProcedureOverviewFragmentDirections.actionProcedureOverviewFragment2ToProcedureListFragment()
            findNavController().navigate(action)
        }

        var btnBack = dialog.findViewById<Button>(R.id.btnBack)
        var txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        var txtDescription = dialog.findViewById<TextView>(R.id.txtDescription)

        txtTitle.setTextColor(Color.RED)
        txtDescription.text = errorMsg

        btnBack.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}