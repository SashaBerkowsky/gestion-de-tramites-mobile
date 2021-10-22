package com.ort.gestiondetramitesmobile.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureFormViewModel

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat


class ProcedureFormFragment : Fragment() {

    companion object {
        fun newInstance() = ProcedureFormFragment()
    }
    lateinit var v: View
    private val viewModel: ProcedureFormViewModel by viewModels()
    lateinit var  myContext: FragmentActivity
    lateinit var edtDni: EditText
    lateinit var edtName: EditText
    lateinit var edtSurname: EditText
    lateinit var edtAddress: EditText
    lateinit var btnContinue: Button
    lateinit var txtProcedureName: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        v = inflater.inflate(R.layout.procedure_form_fragment, container, false)

        //Set current user data
        viewModel.setCurrentUser()


        btnContinue = v.findViewById(R.id.btn_continue)

        edtDni = v.findViewById(R.id.edtDni)
        edtName = v.findViewById(R.id.edtName)
        edtSurname = v.findViewById(R.id.edtSurname)
        edtAddress = v.findViewById(R.id.edtAddress)
        txtProcedureName = v.findViewById(R.id.tv_procedure_name)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel

    }

    override fun onAttach(context: Context) {
        myContext = activity as FragmentActivity
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()

        // Date picker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        var textInputBirthday = v.findViewById<MaterialButton>(R.id.ti_birthday)
        picker.addOnPositiveButtonClickListener {
            // Do something...
            val outputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            val text = outputDateFormat.format(it)
            textInputBirthday.text = text
        }
        textInputBirthday.setOnClickListener {
            picker.show(myContext.supportFragmentManager, picker.toString())

        }

        // First select
        val items = viewModel.licenceTypesTitles //listOf("Primera licencia", "Renovación")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        var autoCompleteTextView = v.findViewById<AutoCompleteTextView>(R.id.ac_procedure)
        autoCompleteTextView.setAdapter(adapter)

        // Second select
        val items2 = viewModel.licenceCodes //listOf("A1", "B1", "C1")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.list_item, items2)
        var autoCompleteTextView2 = v.findViewById<AutoCompleteTextView>(R.id.ac_licence_type)
        autoCompleteTextView2.setAdapter(adapter2)

        btnContinue.setOnClickListener {
            val neededPictures = ProcedureFormFragmentArgs.fromBundle(requireArguments()).neededPictures

            viewModel.setProcedureUser(edtName.text.toString(),edtSurname.text.toString(), edtDni.text.toString(),
                edtAddress.text.toString(),textInputBirthday.text.toString())
            viewModel.createProcedure(autoCompleteTextView.text.toString(), autoCompleteTextView2.text.toString())

            if(viewModel.isDataValid()){
                val action = ProcedureFormFragmentDirections.actionProcedureFormFragment2ToPictureStepperFragment(0,neededPictures,viewModel.getProcedure())
                findNavController().navigate(action)
            }

        }
        txtProcedureName.text = ProcedureFormFragmentArgs.fromBundle(requireArguments()).procedureTitle
        edtDni.setText(viewModel.getDni())
        edtName.setText(viewModel.getName())
        edtSurname.setText(viewModel.getSurname())
        edtAddress.setText( viewModel.getAddress())
        textInputBirthday.setText(viewModel.getBirthdate())
    }

}


