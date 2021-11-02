package com.ort.gestiondetramitesmobile.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.activities.HomeActivity
import com.ort.gestiondetramitesmobile.viewmodels.UserDataFormViewModel
import java.text.SimpleDateFormat
import java.util.*

class UserDataFormFragment : Fragment() {

    companion object {
        fun newInstance() = UserDataFormFragment()
    }

    private lateinit var viewModel: UserDataFormViewModel
    private lateinit var v: View
    private lateinit var saveButton: Button
    lateinit var edtDni: EditText
    lateinit var edtName: EditText
    lateinit var edtSurname: EditText
    lateinit var edtAddress: EditText
    lateinit var  myContext: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         v = inflater.inflate(R.layout.user_data_form_fragment, container, false)
        edtDni = v.findViewById(R.id.UserDataForm_DNI)
        edtName = v.findViewById(R.id.UserDataForm_Name)
        edtSurname = v.findViewById(R.id.UserDataForm_LastName)
        edtAddress = v.findViewById(R.id.UserDataForm_Address)
        saveButton = v.findViewById(R.id.UserDataForm_SaveButton)
        return v
    }

    override fun onStart() {
        super.onStart()
        // Date picker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        var textInputBirthday = v.findViewById<MaterialButton>(R.id.UserDataForm_dateOfBirth)
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
        saveButton.setOnClickListener{
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserDataFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

}