package com.ort.gestiondetramitesmobile.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.viewmodels.NewProcedureViewModel
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.signin.GoogleSignInOptions







class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var v: View
    private lateinit var viewModel: NewProcedureViewModel
    private lateinit var btnLogin : Button
    private lateinit var btnGoogle : SignInButton
    private lateinit var txtGoogle : String

    private lateinit var userList: MutableList<User>
    private var i: Int = 0
    private var lastUserId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        v = inflater.inflate(R.layout.login_fragment, container, false)

        btnLogin = v.findViewById(R.id.btn_login)
        btnGoogle = v.findViewById(R.id.sign_in_button)
        txtGoogle = "Iniciar sesión con Google"

        setGooglePlusButtonText(btnGoogle,txtGoogle)

        return v
    }

    private fun navToHomeActivity() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeActivity()
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()


        btnLogin.setOnClickListener {
            navToHomeActivity()

        /*       val searchedUser = findUser()
            if(searchedUser == null) {
                i += 1
                Snackbar.make(binding.root, "Usuario agregado", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(binding.root, "El usuario ya existe", Snackbar.LENGTH_LONG).show()
            }
        }*/

        }

        /*  private fun findUser(): User? {

        var idx = 0
        var searchedUser : User? = null
        var user : User?
        userList = userDao?.loadAllPersons() as MutableList<User>

        while ( idx < userList.size && searchedUser == null ) {
            user = userList[idx]

            if (user.name == binding.name.text.toString() && user.password == binding.password.text.toString()) {
                searchedUser = user
                Log.d("sU","Usuario encontrado. ID: " + searchedUser.id)
            }
            idx++
        }
        return searchedUser
    }

    fun getUser(): User? {
        return userDao?.loadPersonById(lastUserId)
    }*/
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