package com.shahad.instic.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.shahad.instic.R
import com.shahad.instic.databinding.ActivityAuthBinding
import com.shahad.instic.ui.InsticViewModel

class AuthActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: InsticViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        
        supportActionBar!!.hide()
    }
    
    fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }
    
}