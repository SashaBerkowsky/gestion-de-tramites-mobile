package com.ort.gestiondetramitesmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.Tramite

class TramiteAdapter (
    private var tramiteList: MutableList<Tramite>
    ) : RecyclerView.Adapter<TramiteAdapter.TramiteHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TramiteAdapter.TramiteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tramite,parent,false)
        return(TramiteHolder(view))
    }

    override fun onBindViewHolder(holder: TramiteAdapter.TramiteHolder, position: Int) {
        holder.setName(tramiteList[position].name)
        holder.setDateTramite(tramiteList[position].entregado)
        holder.setState(tramiteList[position].state)


    }

    override fun getItemCount(): Int {
        return tramiteList.size
    }

    class TramiteHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View
        init {
            this.view = v
        }

        fun setName(TramiteName : String){
            val txt: TextView = view.findViewById(R.id.TramiteName)
            txt.text = TramiteName
        }
        fun setDateTramite(dateTramite : String){
            val txt: TextView = view.findViewById(R.id.DateTramite)
            txt.text = dateTramite
        }
        fun setState(StateTramite : String){
            val txt: TextView = view.findViewById(R.id.StateTramite)
            txt.text = StateTramite
        }
        fun changeColor() {
            val c: CardView = view.findViewById(R.id.cardTramite)

        }
    }
}