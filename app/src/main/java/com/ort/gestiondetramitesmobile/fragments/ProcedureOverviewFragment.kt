package com.ort.gestiondetramitesmobile.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.models.TramiteLicenciaConducir
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureOverviewViewModel

class ProcedureOverviewFragment : Fragment() {

    companion object {
        fun newInstance() = ProcedureOverviewFragment()
    }

    private lateinit var viewModel: ProcedureOverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.procedure_overview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProcedureOverviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun obtenerTramite(): TramiteLicenciaConducir {

        var user : User = User("ecapurisse@gmail.com", "123456")
        user.name = "Edgard"
        user.surname = "Capurisse"
        user.birthdate = "30/12/81"
        user.dni = "92876136"
        user.address = "en la casa de Mimi :P"

        var TramiteLicenciaConducir : TramiteLicenciaConducir = TramiteLicenciaConducir(user, "VC-0012", "No", "")
        TramiteLicenciaConducir.tipoLicencia = "Licencia XDF"
        TramiteLicenciaConducir.foto1URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto2URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto3URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto4URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto5URL = "https://picsum.photos/id/1/200/300"
        TramiteLicenciaConducir.foto6URL = "https://picsum.photos/id/1/200/300"

        return TramiteLicenciaConducir
    }

}