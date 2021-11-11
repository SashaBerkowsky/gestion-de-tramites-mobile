package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.activities.HomeActivity
import com.ort.gestiondetramitesmobile.models.UserToCreate
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
    lateinit var edtDatePicker: EditText
    private lateinit var dniLayout: TextInputLayout
    private lateinit var nameLayout: TextInputLayout
    private lateinit var surnameLayout: TextInputLayout
    private lateinit var addressLayout: TextInputLayout
    private lateinit var birthdateLayout: TextInputLayout
    lateinit var myContext: FragmentActivity
    private lateinit var dialog: Dialog


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
        edtDatePicker = v.findViewById(R.id.UserDataForm_date_picker)
        dniLayout = v.findViewById(R.id.til_dni)
        nameLayout = v.findViewById(R.id.til_name)
        surnameLayout = v.findViewById(R.id.til_lastName)
        addressLayout = v.findViewById(R.id.til_address)
        birthdateLayout = v.findViewById(R.id.til_date_picker)
        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)

        return v
    }

    override fun onStart() {
        super.onStart()
        myContext = activity as FragmentActivity
        // Date picker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener {
            // Do something...
            val outputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            val text = outputDateFormat.format(it)
            edtDatePicker.setText(text)
        }
        edtDatePicker.setOnClickListener {
            picker.show(myContext.supportFragmentManager, picker.toString())
        }

        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)


        var savedEmail = sharedPref.getString("userEmail", "")!!
        Log.d("EMAILLLL", savedEmail)

        // TODO: use method to format date correctly
        saveButton.setOnClickListener {
            Log.d("date",edtDatePicker.text.toString() )
            dialog.show()
            val userToCreate = UserToCreate(
                name = edtName.text.toString(),
                email = savedEmail,
                surname = edtSurname.text.toString(),
                dni = edtDni.text.toString(),
                inBirthdate = edtDatePicker.text.toString(),
                address = edtAddress.text.toString(),
            )
            if(isFormValid(userToCreate.dni,userToCreate.name, userToCreate.surname, userToCreate.address, userToCreate.birthdate )){
                viewModel.setCreateUser(userToCreate) { isUserCreated, userID ->
                    dialog.dismiss()
                    if (isUserCreated) {
                        if (userID !== null) {
                            val editor = sharedPref.edit()
                            editor.putInt("userID", userID)
                            editor.apply()
                        }
                        val action = UserDataFormFragmentDirections.actionUserDataFormFragmentToHomeActivity()
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.procedureListFragment, true)
                            .build();
                        findNavController().navigate(action, navOptions)
                    }
                }
            }  else{
                dialog.dismiss()
            }


        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserDataFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun isFormValid(dni: String, name: String, surname: String, address: String, birthdate: String): Boolean {
        return when {
            dni.isEmpty() ->{
                dniLayout.error = "Campo Requerido"
                false
            }
            dni.length != 8->{
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
                birthdateLayout.error = "Campo requerido"
                false
            }
            else -> {
                true
            }
        }
    }

}