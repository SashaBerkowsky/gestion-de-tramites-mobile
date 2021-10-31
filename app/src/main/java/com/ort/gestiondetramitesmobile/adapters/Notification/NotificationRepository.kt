package com.ort.gestiondetramitesmobile.adapters.Notification

import com.ort.gestiondetramitesmobile.api.RetrofitInstance

class NotificationRepository (private val retrofit: RetrofitInstance){

    fun getNotificationList(idUser : Int) = retrofit.apiNotification.getNotificationList(idUser)
}