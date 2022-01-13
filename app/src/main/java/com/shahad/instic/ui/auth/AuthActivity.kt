package com.shahad.instic.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.shahad.instic.R
import com.shahad.instic.databinding.ActivityAuthBinding
import com.shahad.instic.ui.InsticViewModel
import com.shahad.instic.ui.home.MainActivity

class AuthActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityAuthBinding
	private val viewModel: InsticViewModel by viewModels()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityAuthBinding.inflate(layoutInflater, null, false)
		setContentView(binding.root)
		
		FirebaseAuth.getInstance().currentUser?.let {
			Intent(this, MainActivity::class.java).also { startActivity(it) }
			finish()
		}
	}
	
	fun navigateTo(fragment: Fragment) {
		supportFragmentManager.beginTransaction()
			.replace(R.id.auth_fragmentContainerView, fragment)
			.addToBackStack(null)
			.commit()
	}
	
}