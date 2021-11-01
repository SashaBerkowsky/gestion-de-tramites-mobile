package com.ort.gestiondetramitesmobile.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.adapters.ProcedureListAdapterOld
import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureListOldViewModel

class ProcedureListOldFragment : Fragment() {

    private lateinit var v: View
    private lateinit var  recProcedure : RecyclerView
    private val adapter = ProcedureListAdapterOld {
        onItemClick(it)
    }
    private val viewModel : ProcedureListOldViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.procedure_list_old_fragment, container, false)
        recProcedure = v.findViewById(R.id.rec_OldProcedures)

        var btnCreateNew = v.findViewById<FloatingActionButton>(R.id.btn_create_procedure)
        btnCreateNew.setOnClickListener {
            val action = ProcedureListFragmentDirections.actionProcedureListFragmentToNewProcedureFragment2()
            findNavController().navigate(action)
        }

        recProcedure.adapter = adapter

        viewModel.procedureList.observe(viewLifecycleOwner, Observer {
            Log.d( "Procedure Old","onCreate: $it")
            adapter.setProcedureListItems(it)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                this.context,
                "Se ha producido un error al traer los trámites",
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.getProceduresList()

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun onItemClick(selectedIdx: Int){
        val selectedProcedure = viewModel.getSelectedProcedure(selectedIdx)
        val action = selectedProcedure?.let {
            ProcedureListFragmentDirections.actionProcedureListFragmentToProcedureDetailFragment(it)
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    override fun onStart(){
        super.onStart()
        recProcedure.setHasFixedSize(true)
        recProcedure.layoutManager = LinearLayoutManager(context)
    }
}




