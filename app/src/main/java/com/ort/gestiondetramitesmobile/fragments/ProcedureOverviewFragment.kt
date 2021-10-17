package com.ort.gestiondetramitesmobile.fragments

import android.app.Dialog
import android.graphics.Color
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

    private fun showErrorDialog(errorMsg: String){
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.state_dialog)

        var btnBack = dialog.findViewById<Button>(R.id.btnBack)
        var txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        var txtDescription = dialog.findViewById<TextView>(R.id.txtDescription)

        txtTitle.setTextColor(Color.RED)
        txtDescription.text = errorMsg

        btnBack.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}