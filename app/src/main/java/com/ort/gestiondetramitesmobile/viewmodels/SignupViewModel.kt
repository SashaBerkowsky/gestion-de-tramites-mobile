package com.ort.gestiondetramitesmobile.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.adapters.UserRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    private val repository = UserRepository(RetrofitInstance)

    fun getCurrentUser(email: String, onComplete: (Boolean, Int?) -> Unit) {
        val response = repository.getUser(email)
        response.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("body", response.body()?.id.toString())
                onComplete(response.code() == 404, response.body()?.id)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}