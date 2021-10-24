package com.ort.gestiondetramitesmobile.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =inflater.inflate(R.layout.change_password_fragment, container, false)
        email = v.findViewById(R.id.email)
        btnSend =v.findViewById(R.id.btn_continue)
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
            if(email.text.isNotEmpty()){
                resetPassword()
            }else {
                Toast.makeText(this.context,"debe ingresar el correo",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun resetPassword(){
        auth.setLanguageCode("es")
        auth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener{
            if (it.isSuccessful) {
                Toast.makeText(this.context,"se ha enviado un correo para restablecer la contraceña",Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(
                    this.context,
                    "se ha producido un error al enviar correo",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}