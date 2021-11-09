package com.ort.gestiondetramitesmobile.adapters

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.Procedure
import java.text.SimpleDateFormat
import java.util.*

class ProcedureListAdapterOld (val onItemClick : (Int) -> Unit): RecyclerView.Adapter<ProcedureListAdapterOld.ProcedureHolder>(){

   var procedureList = mutableListOf<Procedure>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProcedureHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_procedure_list_old,parent,false)
        return(ProcedureHolder(view))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ProcedureHolder, position: Int) {
        holder.setName(procedureList[position].getCurrentProcedureType().title)
        holder.setDate(procedureList[position].getFormatedCreationDate())
        holder.setState(procedureList[position])
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
        //TODO mover esto a dao
        fun setDate(dateStr : String){
           val txt: TextView = view.findViewById(R.id.procedureDate)

            txt.text = dateStr
        }
        fun setState(procedure : Procedure){
            val txt: TextView = view.findViewById(R.id.stateProcedure)
            val state: String = procedure.getCurrentProcedureState().title

            if(procedure.isProcedureCanceled()){
                txt.setTextColor(Color.parseColor(procedure.getCurrentProcedureState().color))
                txt.text = "Rechazado"
            } else{
                txt.text = state
            }

        }
        fun getCardView () : CardView {
            return view.findViewById(R.id.procedure_list_card)
        }
    }

}