package com.ort.gestiondetramitesmobile.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.notification_fragment, container, false)
        recNotification = v.findViewById(R.id.rec_Notification_list)

        recNotification.adapter = adapter

        viewModel.notificationList.observe(viewLifecycleOwner, Observer {
            adapter.setNotificationListItems(it)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {

        })


        viewModel.getNotificationList()

        return v
    }


    override fun onStart(){
        super.onStart()
        recNotification.setHasFixedSize(true)
        recNotification.layoutManager = LinearLayoutManager(context)
    }
}