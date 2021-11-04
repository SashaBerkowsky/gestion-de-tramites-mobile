package com.ort.gestiondetramitesmobile.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.gestiondetramitesmobile.adapters.UserRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.models.User
import com.ort.gestiondetramitesmobile.models.UserToCreate
import com.squareup.okhttp.Callback
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.Response

class UserDataFormViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    private val repository = UserRepository(RetrofitInstance)
    fun setCreateUser(user: UserToCreate, onResult: (Boolean) -> Unit) {
        repository.postUser(user).enqueue(object : retrofit2.Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.i("", "post submitted to API." + response.body()!!)
                onResult(response.isSuccessful())

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}

