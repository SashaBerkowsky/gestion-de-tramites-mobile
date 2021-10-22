package com.ort.gestiondetramitesmobile.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.ort.gestiondetramitesmobile.R
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.gestiondetramitesmobile.activities.HomeActivity
import com.ort.gestiondetramitesmobile.activities.LoginActivity


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
         const val RC_SIGN_IN = 4926
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var v: View
    private lateinit var btnLogin : Button
    private lateinit var btnGoogle : SignInButton
    private lateinit var txtGoogle : String
    private lateinit var signUp : TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = Firebase.auth
        googleSignInClient = GoogleSignIn.getClient(requireContext(), getGSO())
        btnGoogle.setOnClickListener { signIn() }
    }

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
        // Check if user is signed in (non-null) and update UI accordingly.
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
        return  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
                    updateUI(null)
                }
            }
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser == null){
            return
        }
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }
}