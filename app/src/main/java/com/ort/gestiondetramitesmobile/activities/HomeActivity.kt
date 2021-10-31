package com.ort.gestiondetramitesmobile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.fragments.NotificationFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar))

        navHostFragment = supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment

        bottomNavView = findViewById(R.id.bottomNav)

        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = when(item.itemId) {

            R.id.action_notifications -> navHostFragment.navController.navigate(R.id.notificationFragment)

            else -> ""
        }
        return super.onOptionsItemSelected(item)
    }

}