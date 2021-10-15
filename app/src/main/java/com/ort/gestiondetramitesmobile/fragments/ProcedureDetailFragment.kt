package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.viewmodels.ProcedureDetailViewModel
import android.content.res.Resources.Theme




class ProcedureDetailFragment : Fragment() {

    private lateinit var v: View

    private lateinit var btnCheckState: Button

    companion object {
        fun newInstance() = ProcedureDetailFragment()
    }

    private lateinit var viewModel: ProcedureDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.procedure_detail_fragment, container, false)

        btnCheckState = v.findViewById(R.id.btnCheckState)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnCheckState.setOnClickListener {
            showStateDialog()
        }
    }

    private fun showStateDialog(){
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.state_dialog)

        var btnBack = dialog.findViewById<Button>(R.id.btnBack)
        var txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        var txtDescription = dialog.findViewById<TextView>(R.id.txtDescription)

        btnBack.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProcedureDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}