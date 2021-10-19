package com.ort.gestiondetramitesmobile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.ort.gestiondetramitesmobile.R
import android.widget.TextView
import com.google.android.gms.common.SignInButton


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var v: View
    private lateinit var btnLogin : Button
    private lateinit var btnGoogle : SignInButton
    private lateinit var txtGoogle : String
    private lateinit var signUp : TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        v = inflater.inflate(R.layout.login_fragment, container, false)

        btnLogin = v.findViewById(R.id.btn_login)
        btnGoogle = v.findViewById(R.id.sign_in_button)
        signUp = v.findViewById(R.id.account2)
        txtGoogle = "Iniciar sesión con Google"

        setGooglePlusButtonText(btnGoogle,txtGoogle)

        return v
    }

    private fun navToHomeActivity() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeActivity()
        findNavController().navigate(action)
    }

    private fun navToSignUpFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()

        btnLogin.setOnClickListener {
            navToHomeActivity()

        }

        signUp.setOnClickListener {
            navToSignUpFragment()
        }
    }

    private fun setGooglePlusButtonText(signInButton: SignInButton, buttonText: String?) {
        for (i in 0 until signInButton.childCount) {
            val v = signInButton.getChildAt(i)
            if (v is TextView) {
                v.text = buttonText
                return
            }
        }
    }
}