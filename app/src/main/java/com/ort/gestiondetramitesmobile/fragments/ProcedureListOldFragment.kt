package com.ort.gestiondetramitesmobile.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.Adapter.TramiteAdapter
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.Tramite
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureListOldViewModel

class ProcedureListOldFragment : Fragment() {

    private lateinit var v: View
    private lateinit var  recTramite : RecyclerView
    lateinit var adapter: TramiteAdapter

    private lateinit var viewModel: ProcedureListOldViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.procedure_list_old_fragment, container, false)
        recTramite = v.findViewById(R.id.ReciclerTramiteOld)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProcedureListOldViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart(){
        super.onStart()
        recTramite.setHasFixedSize(true)
        recTramite.layoutManager= LinearLayoutManager(context)
        recTramite.adapter = TramiteAdapter(obtenerTramites())
    }

    private fun obtenerTramites(): MutableList<Tramite> {

        var tramiteList : MutableList<Tramite> = mutableListOf()
        /*me imagino que aca se llama a un endpoint*/
        tramiteList.add(Tramite("LICENCIA DE CONDUCIR","20/08/2021","Finalizado"))
        tramiteList.add(Tramite("LICENCIA DE CONDUCIR","22/08/2021","Finalizado"))
        tramiteList.add(Tramite("LICENCIA DE CONDUCIR","01/10/2021","Rechazado"))
        tramiteList.add(Tramite("LICENCIA DE CONDUCIR","01/10/2021","Rechazado"))
        tramiteList.add(Tramite("LICENCIA DE CONDUCIR","01/10/2021","Finalizado"))
        tramiteList.add(Tramite("LICENCIA DE CONDUCIR","01/10/2021","Rechazado"))
        return tramiteList
    }
}