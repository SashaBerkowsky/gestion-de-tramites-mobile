package com.ort.gestiondetramitesmobile.adapters.Notification

import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.NotificationReaded
import okhttp3.RequestBody

class NotificationRepository (private val retrofit: RetrofitInstance){

    fun getNotificationList(idUser : String) = retrofit.apiNotification.getNotificationList(idUser)
    suspend fun putNotificationReaded(notificationReaded : NotificationReaded) = retrofit.apiNotification.putNotificationReaded(notificationReaded)
}