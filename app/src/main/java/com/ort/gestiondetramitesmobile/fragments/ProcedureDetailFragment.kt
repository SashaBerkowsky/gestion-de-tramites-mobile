package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureDetailViewModel
import android.content.res.Resources.Theme
import android.graphics.Color
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.gestiondetramitesmobile.adapters.ImageListAdapter
import com.ort.gestiondetramitesmobile.adapters.ProcedureRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.models.ProcedureState


class ProcedureDetailFragment : Fragment() {

    private lateinit var v: View

    private lateinit var btnCheckState: Button
    private lateinit var txtProcedureName: TextView
    private lateinit var edtProcedureType: EditText
    private lateinit var edtDni: EditText
    private lateinit var edtName: EditText
    private lateinit var edtSurname: EditText
    private lateinit var edtBirthdate: EditText
    private lateinit var edtAddress: EditText
    private lateinit var edtLicenceCode: EditText
    private lateinit var dialog: Dialog
    private lateinit var imagesRec: RecyclerView

    companion object {
        fun newInstance() = ProcedureDetailFragment()
    }

    private val viewModel: ProcedureDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.procedure_detail_fragment, container, false)

        btnCheckState = v.findViewById(R.id.btnCheckState)
        txtProcedureName = v.findViewById(R.id.txtProcedureName)
        edtProcedureType = v.findViewById(R.id.edtProcedureType)
        edtDni = v.findViewById(R.id.edtDniNumber)
        edtName = v.findViewById(R.id.edtName)
        edtSurname = v.findViewById(R.id.edtSurname)
        edtBirthdate = v.findViewById(R.id.edtBirthdate)
        edtLicenceCode = v.findViewById(R.id.edtLicenceType)
        edtAddress = v.findViewById(R.id.edtAddress)
        imagesRec = v.findViewById(R.id.recImages)

        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)

        dialog.show()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onStart() {
        super.onStart()


        var idProcedure = ProcedureDetailFragmentArgs.fromBundle(requireArguments()).idProcedure
        viewModel.setProcedure(idProcedure){
            edtName.setText(viewModel.getUserName())
            edtSurname.setText(viewModel.getUserSurname())
            edtDni.setText(viewModel.getUserDni())
            edtBirthdate.setText(viewModel.getUserBirthdate())
            edtAddress.setText(viewModel.getUserAddress())
            edtLicenceCode.setText(viewModel.getLicenceCode())
            edtProcedureType.setText(viewModel.getProcedureType())
            txtProcedureName.setText(viewModel.getProcedureName())

            val adapter = ImageListAdapter(viewModel.getImagesAsArray(),requireContext())
            imagesRec.adapter = adapter
            imagesRec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            dialog.dismiss()
        }



        btnCheckState.setOnClickListener {
            showStateDialog()
        }
    }

    private fun showStateDialog(){
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.state_dialog)

        var btnBack = dialog.findViewById<Button>(R.id.btnBack)
        var txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        var txtDescription = dialog.findViewById<TextView>(R.id.txtDescription)

        txtDescription.text = viewModel.getDescriptionText()
        txtTitle.text = viewModel.getStateTitle()

       if(!viewModel.isProcedureApproved()){
            txtTitle.setTextColor(Color.parseColor(viewModel.getStateColor()))
        }

        btnBack.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}