package com.ort.gestiondetramitesmobile.adapters.Notification

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.adapters.ProcedureListAdapterCurrent
import com.ort.gestiondetramitesmobile.models.Notification
import com.ort.gestiondetramitesmobile.models.Procedure
import com.ort.gestiondetramitesmobile.models.ProcedureState
import com.ort.gestiondetramitesmobile.models.ProcedureType

class NotificationAdapter () : RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    var notificationList = mutableListOf<Notification>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.NotificationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification,parent,false)
        return(NotificationHolder(view))
    }


    override fun onBindViewHolder(holder: NotificationAdapter.NotificationHolder, position: Int) {
        holder.setMessage(notificationList[position].menssage)
        holder.setDate(notificationList[position].NotificationDate)

    }

    fun setNotificationListItems(notificationList: List<Notification>){
        this.notificationList = notificationList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }


    class NotificationHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View = v

        fun setMessage(message : String){
            val txt: TextView = view.findViewById(R.id.NotificationMessaje)
            txt.text = message
        }

        fun setDate(dateStr : String){
            val txt: TextView = view.findViewById(R.id.NotificationDate)

            txt.text = dateStr
        }


    }
}