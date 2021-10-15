package com.ort.gestiondetramitesmobile.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.adapters.NewProcedureAdapter
import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.viewmodels.NewProcedureViewModel

class NewProcedureFragment : Fragment() {

    private lateinit var v: View
    private lateinit var  recProcedure : RecyclerView
    lateinit var card : CardView

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

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewProcedureViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun getProcedureTypes(): MutableList<Procedure> {

        var procedureList : MutableList<Procedure> = mutableListOf()
        procedureList.add(Procedure("LICENCIA DE CONDUCIR","Para primeras licencias, renovaciones y nuevos ejemplares"))
        return procedureList
    }

    private fun onItemClick(position : Int) {
        val list = getProcedureTypes()
        val action = NewProcedureFragmentDirections.actionNewProcedureFragmentToProcedureFormFragment()
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