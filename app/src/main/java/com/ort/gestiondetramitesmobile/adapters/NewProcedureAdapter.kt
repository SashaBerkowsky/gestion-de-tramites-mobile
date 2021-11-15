package com.ort.gestiondetramitesmobile.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.ProcedureType

class NewProcedureAdapter(private var procedureList: MutableList<ProcedureType>,
                          var context: Context,
                          val onItemClick: (Int) -> Unit
    ) : RecyclerView.Adapter<NewProcedureAdapter.ProcedureHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProcedureHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_procedures,parent,false)
        return(ProcedureHolder(view))
    }

    override fun onBindViewHolder(holder: ProcedureHolder, position: Int) {
        holder.setName(procedureList[position].title)
        holder.setDescription(procedureList[position].description)
        val title : TextView = holder.getCardView().findViewById(R.id.procedureName)
        var cardView : CardView = holder.getCardView().findViewById(R.id.procedureCard)

        if(title.text.toString() == "PRÓXIMAMENTE") {
            cardView.setCardBackgroundColor(Color.parseColor("#F3F3F3"))
        }

        holder.getCardView().setOnClickListener {
            if (title.text.toString() == "LICENCIA DE CONDUCIR") {
            onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return procedureList.size
    }

    fun setAdapterList(list : MutableList<ProcedureType>) {
        procedureList = list
        notifyDataSetChanged()
    }

    class ProcedureHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View
        init {
            this.view = v
        }

        fun setName(name : String){
            val txt: TextView = view.findViewById(R.id.procedureName)
            txt.text = name
        }
        fun setDescription(description : String){
            val txt: TextView = view.findViewById(R.id.procedureDescription)
            txt.text = description
        }

        fun getCardView () : CardView {
            return view.findViewById(R.id.procedureCard)
        }
    }
}