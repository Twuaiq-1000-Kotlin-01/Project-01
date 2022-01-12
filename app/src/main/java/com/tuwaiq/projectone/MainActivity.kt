package com.tuwaiq.projectone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuwaiq.projectone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNav = binding.bottomNav

        val navController = findNavController(R.id.fragmentContainerView)
        bottomNav.background = null

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.loginFragment -> bottomNav.visibility = View.GONE
                R.id.registerFragment -> bottomNav.visibility = View.GONE
                else -> bottomNav.visibility = View.VISIBLE
            }
        }
    }
}