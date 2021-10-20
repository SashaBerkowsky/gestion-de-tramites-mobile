package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.models.TramiteLicenciaConducir
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureFormViewModel
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureOverviewViewModel

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
        edtLicenceType = v.findViewById(R.id.acProcedureType)
        edtLicenceCode = v.findViewById(R.id.acLicenceType)
        btnSendProcedure = v.findViewById(R.id.btnSendProcedure)

        return v
    }

    override fun onStart() {
        super.onStart()

        edtDni.setHint(viewModel.getDni())
        edtName.setHint(viewModel.getName())
        edtSurname.setHint(viewModel.getSurname())
        edtAddress.setHint(viewModel.getAddress())
        edtBirthdate.setHint(viewModel.getBirthdate())
        edtLicenceType.setHint(viewModel.getLicenceType())
        edtLicenceCode.setHint(viewModel.getLicenceCode())

        btnSendProcedure.setOnClickListener {
            val procedureErrorMsg:String  = viewModel.sendProcedure()
            if(procedureErrorMsg.isEmpty()){
                val action = ProcedureOverviewFragmentDirections.actionProcedureOverviewFragment2ToProcedureSendedFragment2()
                findNavController().navigate(action)
            } else{
                showErrorDialog(procedureErrorMsg)
            }
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }


    private fun obtenerTramite(): TramiteLicenciaConducir {

        var user : User = User("ecapurisse@gmail.com", "123456","Edgard",
            "Capurisse","92876136",
            "en la casa de Mimi :P","30/12/81",
            909)

        var TramiteLicenciaConducir : TramiteLicenciaConducir = TramiteLicenciaConducir(user, "VC-0012", "No", "")
        TramiteLicenciaConducir.tipoLicencia = "Licencia XDF"
        TramiteLicenciaConducir.foto1URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto2URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto3URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto4URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto5URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto6URL = "https://picsum.photos/id/1/200/300"

        return TramiteLicenciaConducir
    }


    private fun showErrorDialog(errorMsg: String){
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.state_dialog)

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