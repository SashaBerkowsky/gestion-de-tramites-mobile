package com.ort.gestiondetramitesmobile.fragments

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
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.activities.HomeActivity
import com.ort.gestiondetramitesmobile.activities.LoginActivity
import com.ort.gestiondetramitesmobile.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    lateinit var v: View
    lateinit var btnEditProfile: Button
    lateinit var btnChangePassword: Button
    lateinit var btnCloseSession: Button
    private lateinit var auth: FirebaseAuth

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.profile_fragment, container, false)

        btnEditProfile = v.findViewById(R.id.btnEditProfile)
        btnChangePassword = v.findViewById(R.id.btnChangePassword)
        btnCloseSession = v.findViewById(R.id.closeSession)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        // GET userID from shared preferences, this was set in LoginFragment
        var userID = sharedPref.getInt("userID", 0)!!
        Log.d("TAG",userID.toString())


        btnCloseSession.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            navToSingInActivity()
        }

        btnEditProfile.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }

        btnChangePassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        }
    }

    private fun navToSingInActivity() {
        val action = ProfileFragmentDirections.actionProfileFragmentToLoginActivity()
        findNavController().navigate(action)
    }
}