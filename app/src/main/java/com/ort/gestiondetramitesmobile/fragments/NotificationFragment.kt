package com.ort.gestiondetramitesmobile.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.adapters.Notification.NotificationAdapter
import com.ort.gestiondetramitesmobile.adapters.ProcedureListAdapterCurrent
import com.ort.gestiondetramitesmobile.viewmodels.NotificationViewModel

class NotificationFragment : Fragment() {

    companion object {
        fun newInstance() = NotificationFragment()
    }

    private lateinit var v: View
    private lateinit var  recNotification : RecyclerView
    private  val adapter = NotificationAdapter()
    private lateinit var notificationEmpty : TextView

    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.notification_fragment, container, false)
        recNotification = v.findViewById(R.id.rec_Notification_list)
        notificationEmpty = v.findViewById(R.id.notification_empty)

        recNotification.adapter = adapter

        viewModel.notificationList.observe(viewLifecycleOwner, Observer {

            if(it.isNotEmpty()) {
                notificationEmpty.visibility = View.GONE
            } else {
                notificationEmpty.visibility = View.VISIBLE
            }

            adapter.setNotificationListItems(it)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {

        })

        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        var userId = sharedPref.getInt("userID", 0)!!
        viewModel.getNotificationList(userId.toString())

        return v
    }


    override fun onStart(){
        super.onStart()
        recNotification.setHasFixedSize(true)
        recNotification.layoutManager = LinearLayoutManager(context)
    }
}