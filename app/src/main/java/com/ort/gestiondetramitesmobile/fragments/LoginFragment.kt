package com.ort.gestiondetramitesmobile.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.navigation.fragment.findNavController
import com.ort.gestiondetramitesmobile.R
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.gestiondetramitesmobile.activities.HomeActivity
import com.ort.gestiondetramitesmobile.activities.LoginActivity
import com.ort.gestiondetramitesmobile.viewmodels.LoginViewModel
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureListCurrentViewModel
import kotlinx.coroutines.*


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
        const val RC_SIGN_IN = 4926
    }

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var v: View
    private lateinit var btnLogin: Button
    private lateinit var btnGoogle: SignInButton
    private lateinit var txtGoogle: String
    private lateinit var signUp: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var password_login: TextInputLayout
    private lateinit var email_login: TextInputLayout
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = Firebase.auth
        googleSignInClient = GoogleSignIn.getClient(requireContext(), getGSO())
        btnGoogle.setOnClickListener { signIn() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.login_fragment, container, false)

        btnLogin = v.findViewById(R.id.btn_login)
        btnGoogle = v.findViewById(R.id.sign_in_button)
        signUp = v.findViewById(R.id.account2)
        txtGoogle = "Iniciar sesión con Google"
        email = v.findViewById(R.id.Email)
        password = v.findViewById(R.id.Pass)
        email_login = v.findViewById(R.id.email_login)
        password_login = v.findViewById(R.id.password_login)
        setGooglePlusButtonText(btnGoogle, txtGoogle)

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
            logInWithEmailAndPass()
        }

        signUp.setOnClickListener {
            navToSignUpFragment()
        }
//         Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
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

    private fun getGSO(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.i("onActivityResult success", account.id)
                // Acá se puede mandar al back lo que devuelva firebase
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                //handle error
                Log.i("onActivityResult error", e.message.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI go to home activity
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.d("user error", "error")
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser == null) {
            return
        }

        viewModel.getCurrentUser(currentUser.email.toString()) { isNewUser, userID ->
            setSharedPreferences(userID)
            var action: NavDirections = if (isNewUser) {
                LoginFragmentDirections.actionLoginFragmentToUserDataFormFragment()
            } else {
                LoginFragmentDirections.actionLoginFragmentToHomeActivity()
            }

            findNavController().navigate(action)
        }

    }

    private fun setSharedPreferences(userID: Int?) {
        if(userID !== null){
            val USER_PREF = "userPreferences"
            val sharedPref: SharedPreferences = requireContext().getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            editor.putInt("userID", userID)
            editor.apply()
        }
    }


    private fun logInWithEmailAndPass() {

        if (validateForm(email.text.toString(), password.text.toString())) {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        email_login.error = " "
                        password_login.error = "Usuario o contraseña incorrecto"
                    }
                }
        }

    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                email_login.error = "Campo requerido"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                email_login.error = "Email inválido"
                false
            }
            TextUtils.isEmpty(password) -> {
                password_login.error = "Campo requerido"
                false
            }
            password.length < 4 -> {
                password_login.error = "La contraseña debe tener al menos 4 caracteres"
                false
            }

            else -> {
                true
            }
        }
    }
}