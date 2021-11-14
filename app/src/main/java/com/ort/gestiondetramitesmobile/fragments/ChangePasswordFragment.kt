package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.activities.HomeActivity
import com.ort.gestiondetramitesmobile.viewmodels.ChangePasswordViewModel

class ChangePasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ChangePasswordFragment()
    }
    private lateinit var v: View
    private lateinit var email : EditText
    private lateinit var btnSend : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var change_email:TextInputLayout
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =inflater.inflate(R.layout.change_password_fragment, container, false)
        email = v.findViewById(R.id.email)
        btnSend =v.findViewById(R.id.btn_continue)
        change_email = v.findViewById(R.id.change_email)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        btnSend.setOnClickListener{
            dialog = Dialog(requireContext())

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.loading_dialog)

            if(email.text.isNotEmpty()){
                resetPassword()
            }else {
                Toast.makeText(this.context,"Debe ingresar el correo",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetPassword(){
        if(validateForm(email.text.toString())){
            dialog.show()
        auth.setLanguageCode("es")
        auth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener{
            dialog.dismiss()
            if (it.isSuccessful) {
                val snack = Snackbar.make(v,"Se ha enviado un correo para restablecer la contraseña", Snackbar.LENGTH_SHORT)
                snack.show()
               }else {
                val snack = Snackbar.make(v,"Se ha producido un error al enviar correo", Snackbar.LENGTH_SHORT)
                snack.show()
            }
        }
     }
    }
    private fun validateForm(email: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                change_email.error = "Campo requerido"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                change_email.error = "Email inválido"
                false
            }

            else -> {
                true
            }
        }
    }
}