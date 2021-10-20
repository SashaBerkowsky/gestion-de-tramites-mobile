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
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureSendedViewModel

class ProcedureSendedFragment : Fragment() {

    private lateinit var v : View

    lateinit var btnHome: Button

    companion object {
        fun newInstance() = ProcedureSendedFragment()
    }

    private lateinit var viewModel: ProcedureSendedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.procedure_sended_fragment, container, false)

        btnHome = v.findViewById(R.id.btnHome)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProcedureSendedViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        btnHome.setOnClickListener {
            val action = ProcedureSendedFragmentDirections.actionProcedureSendedFragment2ToProcedureListFragment()
            findNavController().navigate(action)
        }
    }

}