package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureFormViewModel

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Patterns
import android.view.Window
import android.widget.*
import androidx.core.view.isEmpty
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.util.*
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
    private lateinit var selectProcedureLayout: TextInputLayout
    private lateinit var dniLayout: TextInputLayout
    private lateinit var nameLayout: TextInputLayout
    private lateinit var surnameLayout: TextInputLayout
    private lateinit var addressLayout: TextInputLayout
    private lateinit var birhtdateLayout: TextInputLayout
    private lateinit var licenceTypeLayout: TextInputLayout
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        v = inflater.inflate(R.layout.procedure_form_fragment, container, false)

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        var userId = sharedPref.getInt("userID", 0)!!

        viewModel.setCurrentUser(userId)

        btnContinue = v.findViewById(R.id.btn_continue)

        edtDni = v.findViewById(R.id.edtDni)
        edtName = v.findViewById(R.id.edtName)
        edtSurname = v.findViewById(R.id.edtSurname)
        edtAddress = v.findViewById(R.id.edtAddress)
        txtProcedureName = v.findViewById(R.id.txtProcedureName)
        selectProcedureLayout = v.findViewById(R.id.ti_select_procedure)
        dniLayout = v.findViewById(R.id.tf_dni)
        nameLayout = v.findViewById(R.id.tf_name)
        surnameLayout = v.findViewById(R.id.tf_lastName)
        addressLayout = v.findViewById(R.id.tf_address)
        licenceTypeLayout = v.findViewById(R.id.ti_licence_type)

        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)

        dialog.show()


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
            if(isFormValid(autoCompleteTextView.text.toString(), edtDni.text.toString(), edtName.text.toString(), edtSurname.text.toString(), edtAddress.text.toString(), textInputBirthday.text.toString(),  autoCompleteTextView2.text.toString())){
                val neededPictures = ProcedureFormFragmentArgs.fromBundle(requireArguments()).neededPictures

                viewModel.setProcedureUser(edtName.text.toString(),edtSurname.text.toString(), edtDni.text.toString(),
                    edtAddress.text.toString(),textInputBirthday.text.toString())
                viewModel.createProcedure(autoCompleteTextView.text.toString(), autoCompleteTextView2.text.toString())


                val action = ProcedureFormFragmentDirections.actionProcedureFormFragment2ToPictureStepperFragment(0,neededPictures,viewModel.getProcedure())
                findNavController().navigate(action)

            }


        }
        viewModel.currentUser.observe(viewLifecycleOwner,{
            var dob = formatBirthdate(viewModel.getBirthdate())
            txtProcedureName.text = ProcedureFormFragmentArgs.fromBundle(requireArguments()).procedureTitle
            edtDni.setText(viewModel.getDni())
            edtName.setText(viewModel.getName())
            edtSurname.setText(viewModel.getSurname())
            edtAddress.setText( viewModel.getAddress())
            textInputBirthday.setText(dob)
            dialog.dismiss()
        })
    }

    private fun formatBirthdate(birthdate : String?) : String {
        var format = SimpleDateFormat("YYYY-MM-dd")
        val newDate: Date = format.parse(birthdate)
        format = SimpleDateFormat("dd/MM/YYYY")
        return format.format(newDate)
    }

    private fun isFormValid(procedureType: String, dni: String, name: String, surname: String, address: String, birthdate: String, licenceCode: String): Boolean {
        return when {
            procedureType.isEmpty() -> {
                selectProcedureLayout.error = "Campo requerido"
                false
            }
            dni.isEmpty() ->{
                dniLayout.error = "Campo Requerido"
                false
            }
            dni.length != 9->{
                dniLayout.error = "Dni invalido"
                false
            }
            name.isEmpty()->{
                nameLayout.error = "Campo requerido"
                false
            }
            surname.isEmpty()->{
                surnameLayout.error = "Campo requerido"
                false
            }
            address.isEmpty()->{
                addressLayout.error = "Campo requerido"
                false
            }
            birthdate.isEmpty()->{
                birhtdateLayout.error = ""
                false
            }
            licenceCode.isEmpty()->{
                licenceTypeLayout.error = ""
                false
            }
            else -> {
                true
            }
        }
    }

}


