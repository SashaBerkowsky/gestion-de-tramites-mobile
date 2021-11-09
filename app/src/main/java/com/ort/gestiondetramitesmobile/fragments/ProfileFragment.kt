package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {

    lateinit var v: View
    lateinit var btnEditProfile: Button
    lateinit var btnChangePassword: Button
    lateinit var btnCloseSession: Button
    private lateinit var auth: FirebaseAuth
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var profileName : TextView
    private lateinit var profileDNI : TextView
    private lateinit var profileAddress : TextView
    private lateinit var profileDob : TextView
    private lateinit var profileEmail : TextView
    private lateinit var dialog : Dialog

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.profile_fragment, container, false)

        btnEditProfile = v.findViewById(R.id.btnEditProfile)
        btnChangePassword = v.findViewById(R.id.btnChangePassword)
        btnCloseSession = v.findViewById(R.id.closeSession)
        profileName = v.findViewById(R.id.profile_name)
        profileDNI = v.findViewById(R.id.profile_DNI)
        profileAddress = v.findViewById(R.id.profile_address)
        profileDob = v.findViewById(R.id.profile_dob)
        profileEmail = v.findViewById(R.id.profile_email)

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        var userId = sharedPref.getInt("userID", 0)!!
        var userEmail = sharedPref.getString("userEmail", "")!!



        viewModel.user.observe(viewLifecycleOwner, Observer {
            var dob = formatBirthdate(viewModel.user.value?.birthdate)
            profileName.text = "Nombre: " + viewModel.user.value?.name + " " + viewModel.user.value?.surname
            profileDNI.text = "DNI: " + viewModel.user.value?.dni
            profileAddress.text = "Dirección: " + viewModel.user.value?.address
            profileDob.text = "Fecha de nacimiento: " + dob
            profileEmail.text = "Email: " + userEmail

            dialog.dismiss()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                this.context,
                "Se ha producido un error al traer el usuario",
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.getUser(userId)

        return v
    }

    private fun formatBirthdate(birthdate : String?) : String {
        var format = SimpleDateFormat("YYYY-MM-dd")
        val newDate: Date = format.parse(birthdate)
        format = SimpleDateFormat("dd/MM/YYYY")
        return format.format(newDate)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onActivityCreated(savedInstanceState)
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

        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)

        dialog.show()
    }

    private fun navToSingInActivity() {
        val action = ProfileFragmentDirections.actionProfileFragmentToLoginActivity()
        findNavController().navigate(action)
    }

}