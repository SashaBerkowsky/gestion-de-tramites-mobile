package com.ort.gestiondetramitesmobile.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    lateinit var v: View
    lateinit var btnEditProfile: Button
    lateinit var btnChangePassword: Button

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v= inflater.inflate(R.layout.profile_fragment, container, false)

        btnEditProfile = v.findViewById(R.id.btnEditProfile)
        btnChangePassword = v.findViewById(R.id.btnChangePassword)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart(){
        super.onStart()

        btnEditProfile.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }

        btnChangePassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        }
    }

}