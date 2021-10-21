package com.ort.gestiondetramitesmobile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.Procedure

class ProcedureListAdapterOld (val onItemClick : (Int) -> Unit): RecyclerView.Adapter<ProcedureListAdapterOld.ProcedureHolder>(){

   var procedureList = mutableListOf<Procedure>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProcedureHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_procedure_list_old,parent,false)
        return(ProcedureHolder(view))
    }

    override fun onBindViewHolder(holder: ProcedureHolder, position: Int) {
        holder.setName(procedureList[position].getCurrentProcedureType().title)
        holder.setDate(procedureList[position].creationDate.toString())
        holder.setState(procedureList[position].getCurrentProcedureState().title)
        holder.getCardView().setOnClickListener {
            onItemClick(position)
        }
    }

    fun setProcedureListItems(procedureList: List<Procedure>){
        this.procedureList = procedureList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return procedureList.size
    }

    class ProcedureHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View = v

        fun setName(name : String){
            val txt: TextView = view.findViewById(R.id.procedureName)
            txt.text = name
        }
        fun setDate(date : String){
            val txt: TextView = view.findViewById(R.id.procedureDate)
            txt.text = date
        }
        fun setState(state : String){
            val txt: TextView = view.findViewById(R.id.stateProcedure)
            txt.text = state
        }
        fun getCardView () : CardView {
            return view.findViewById(R.id.procedure_list_card)
        }
    }

}