package com.ort.gestiondetramitesmobile.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.ort.gestiondetramitesmobile.adapters.UserRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.Address
import com.ort.gestiondetramitesmobile.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    var user = MutableLiveData<User>()
    val errorMessage = MutableLiveData<String>()
    private val repository = UserRepository(RetrofitInstance)

    fun getUser(userId : Int) : MutableLiveData<User> {

        val responseAPI = repository.getUserById(userId)

        responseAPI.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                user.value = response.body()!!
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
        return user
    }

    fun updateAddress(userId : Int, address : Address) {

        repository.updateAddress(userId,address).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.d("response",response.body()!!.address)
                    user.value?.address = response.body()!!.address
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }
        })
    }
}