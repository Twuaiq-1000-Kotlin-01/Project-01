package com.tuwaiq.rr.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tuwaiq.rr.R
import com.tuwaiq.rr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigationView.background = null
    }
}