package com.ort.gestiondetramitesmobile.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureFormViewModel
import android.widget.EditText

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout


class ProcedureFormFragment : Fragment() {

    companion object {
        fun newInstance() = ProcedureFormFragment()
    }
    lateinit var v: View
    private lateinit var viewModel: ProcedureFormViewModel
    lateinit var  myContext: FragmentActivity
    lateinit var btnContinue: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.procedure_form_fragment, container, false)

        // First select
        val items = listOf("Primera licencia", "Renovación")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        var autoCompleteTextView = v.findViewById<AutoCompleteTextView>(R.id.ac_procedure)
        autoCompleteTextView.setAdapter(adapter)

        // Date picker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        var textInputBirthday = v.findViewById<MaterialButton>(R.id.ti_birthday)
        textInputBirthday.setOnClickListener {
            picker.show(myContext.supportFragmentManager, picker.toString())
        }

        // Second select
        val items2 = listOf("A1", "B1", "C1")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.list_item, items2)
        var autoCompleteTextView2 = v.findViewById<AutoCompleteTextView>(R.id.ac_licence_type)
        autoCompleteTextView2.setAdapter(adapter2)
        btnContinue = v.findViewById(R.id.btn_continue)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProcedureFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        myContext = activity as FragmentActivity
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()

        btnContinue.setOnClickListener {
            //Eventualmente el array neededPictures va a venir de la api o algo asi
            val neededPictures = arrayOf("Primera foto", "Segunda foto", "Tercera y ultima foto")
            val action = ProcedureFormFragmentDirections.actionProcedureFormFragment2ToPictureStepperFragment(0,neededPictures)
            findNavController().navigate(action)
        }
    }

}


