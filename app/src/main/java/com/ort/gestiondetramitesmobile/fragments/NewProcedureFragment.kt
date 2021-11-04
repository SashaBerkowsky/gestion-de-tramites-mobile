package com.ort.gestiondetramitesmobile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.adapters.NewProcedureAdapter
import com.ort.gestiondetramitesmobile.models.getProcedureTypes
import com.ort.gestiondetramitesmobile.viewmodels.NewProcedureViewModel

class NewProcedureFragment : Fragment() {

    private lateinit var v: View
    private lateinit var recProcedure : RecyclerView

    companion object {
        fun newInstance() = NewProcedureFragment()
    }

    private lateinit var viewModel: NewProcedureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.new_procedure_fragment, container, false)
        recProcedure = v.findViewById(R.id.recyclerProcedures)

        var prodecureTypesList = mutableListOf<String>()

        getProcedureTypes().forEach() {
            prodecureTypesList.add(it.title)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, prodecureTypesList)
        var autoCompleteTextView = v.findViewById<AutoCompleteTextView>(R.id.procedureType)
        autoCompleteTextView.setAdapter(adapter)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewProcedureViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun onItemClick(position: Int) {
        val action = NewProcedureFragmentDirections.actionNewProcedureFragment2ToProcedureFormFragment2(
            getProcedureTypes()[position].title, getProcedureTypes()[position].neededPictures)
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        recProcedure.setHasFixedSize(true)
        recProcedure.layoutManager = LinearLayoutManager(context)
        recProcedure.adapter = NewProcedureAdapter(getProcedureTypes(), requireContext()) {
            onItemClick(it)
        }
    }

}