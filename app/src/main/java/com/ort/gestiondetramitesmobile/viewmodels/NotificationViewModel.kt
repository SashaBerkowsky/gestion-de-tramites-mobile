package com.ort.gestiondetramitesmobile.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.ort.gestiondetramitesmobile.adapters.Notification.NotificationRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class NotificationViewModel : ViewModel() {

    val notificationList = MutableLiveData<List<Notification>>()
    val errorMessage = MutableLiveData<String>()
    private val repository = NotificationRepository(RetrofitInstance)

    fun getNotificationList() {

        val userId = 3
        val response = repository.getNotificationList(userId)

        response.enqueue(object : Callback<List<Notification>>{
            override fun onResponse(call: Call<List<Notification>>, response: Response<List<Notification>>) {

                    if(!response.body().isNullOrEmpty()) {

                        notificationList.postValue(response.body())
                    }
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                errorMessage.postValue("Error a cargar las notificaciones")
            }

        })
    }

}