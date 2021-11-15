package com.ort.gestiondetramitesmobile.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.adapters.Notification.NotificationRepository
import com.ort.gestiondetramitesmobile.api.RetrofitInstance
import com.ort.gestiondetramitesmobile.fragments.NotificationFragment
import com.ort.gestiondetramitesmobile.models.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var toolbar : Toolbar
    private lateinit var badge : BadgeDrawable

    private val repository = NotificationRepository(RetrofitInstance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment

        bottomNavView = findViewById(R.id.bottomNav)


        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }

    @ExperimentalBadgeUtils
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        badge = BadgeDrawable.create(this)
        badge.isVisible = false
        BadgeUtils.attachBadgeDrawable(badge, toolbar, R.id.action_notifications)

        val sharedPref: SharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val userId = sharedPref.getInt("userID", 0)
        val response = repository.getNotificationList(userId.toString(), true)

        response.enqueue(object : Callback<List<Notification>> {
            override fun onResponse(call: Call<List<Notification>>, response: Response<List<Notification>>) {

                if(response.body()?.size!! > 0) {
                    badge.number = response.body()!!.size
                    badge.isVisible = true
                }
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                //No hago nada
            }
        })

        menuInflater.inflate(R.menu.main_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = when(item.itemId) {

            R.id.action_notifications -> {
                badge.number = 0
                badge.isVisible = false

                navHostFragment.navController.navigate(R.id.notificationFragment)
            }

            else -> ""
        }
        return super.onOptionsItemSelected(item)
    }

}