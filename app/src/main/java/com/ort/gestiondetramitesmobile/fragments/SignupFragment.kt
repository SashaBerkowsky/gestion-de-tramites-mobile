package com.ort.gestiondetramitesmobile.fragments

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.activities.HomeActivity
import com.ort.gestiondetramitesmobile.activities.LoginActivity
import com.ort.gestiondetramitesmobile.viewmodels.SignupViewModel

class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var v: View
    private lateinit var viewModel: SignupViewModel
    private lateinit var signUpTitle: TextView
    private lateinit var inputSignUpEmail: EditText
    private lateinit var inputSignUpPass: EditText
    private lateinit var inputSignUpConfirmPass: EditText
    private lateinit var inputSignUpEmailLayout: TextInputLayout
    private lateinit var inputSignUpPassLayout: TextInputLayout
    private lateinit var inputSignUpConfirmPassLayout: TextInputLayout
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var btnSignUp: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.signup_fragment, container, false)

        btnSignUp = v.findViewById(R.id.btn_sign_up)
        signUpTitle = v.findViewById(R.id.tv_sing_up_title)
        inputSignUpEmail = v.findViewById(R.id.input_sing_up_email)
        inputSignUpPass = v.findViewById(R.id.input_sing_up_pass)
        inputSignUpConfirmPass = v.findViewById(R.id.input_sing_up_confirm_pass)
        inputSignUpEmailLayout = v.findViewById(R.id.input_sing_up_email_layout)
        inputSignUpPassLayout = v.findViewById(R.id.input_sing_up_pass_layout)
        inputSignUpConfirmPassLayout = v.findViewById(R.id.input_sing_up_confirm_pass_layout)
        progressBar = v.findViewById(R.id.progress_bar)
        progressBar.hide()

        return v
    }

    override fun onStart() {
        super.onStart()
        btnSignUp.setOnClickListener {
            Log.i("registeredEmail", "aaaaaaaa")
            registerUser()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun registerUser() {
        // Here we get the text from editText and trim the space
        val email: String = inputSignUpEmail.text.toString()
        val password: String = inputSignUpPass.text.toString()
        val confirmPass: String = inputSignUpConfirmPass.text.toString()

        if (validateForm(email, password, confirmPass)) {
            progressBar.show()
            btnSignUp.isEnabled = false
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        progressBar.hide()
                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            // Registered Email
                            val registeredEmail = firebaseUser.email!!
                            Log.i("registeredEmail", registeredEmail)
                            val intent = Intent(activity, HomeActivity::class.java)
                            startActivity(intent)

                        } else {

                            Toast.makeText(
                                requireActivity(),
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    })
        }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateForm(email: String, password: String, confirmPass: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                inputSignUpEmailLayout.error = "Campo requerido"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                inputSignUpEmailLayout.error = "Email inválido"
                false
            }
            TextUtils.isEmpty(password) -> {
                inputSignUpPassLayout.error = "Campo requerido"
                false
            }
            password.length < 4 -> {
                inputSignUpPassLayout.error = "La contraseña debe tener el menos 4 caracteres"
                false
            }
            TextUtils.isEmpty(confirmPass) -> {
                inputSignUpConfirmPassLayout.error = "Campo requerido"
                false
            }
            password != confirmPass -> {
                inputSignUpConfirmPassLayout.error = "Los campos de password deben ser iguales"
                false
            }
            else -> {
                true
            }
        }
    }


    /**
     * A function to be called the user is registered successfully and entry is made in the firestore database.
     */
    fun userRegisteredSuccess() {

        Toast.makeText(
            requireContext(),
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()


        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }

}