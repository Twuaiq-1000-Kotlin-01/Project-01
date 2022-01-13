package com.tuwaiq.rr.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sandrios.sandriosCamera.internal.SandriosCamera
import com.sandrios.sandriosCamera.internal.ui.model.Media
import com.tuwaiq.rr.R
import com.tuwaiq.rr.databinding.ActivityMainBinding
import com.tuwaiq.rr.presentation.addPost.AddPostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    interface CallBack{
        fun onResultRecived(media: Media)
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.bottomNavigationView.background = null



        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.fab.setOnClickListener {
            navController.navigate(R.id.addPostFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.signupFragment ||
                destination.id == R.id.userInfoFragment) {
                binding.bottomNavigationView.visibility = View.GONE
                binding.bottomAppBar.visibility = View.GONE
                binding.fab.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.fab.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == SandriosCamera.RESULT_CODE && data != null) {
            if (data.getSerializableExtra(SandriosCamera.MEDIA) is Media) {
                val media: Media = (data.getSerializableExtra(SandriosCamera.MEDIA) as Media)
                Log.e("File", "" + media?.path)
                Log.e("Type", "" + media?.type)
                Toast.makeText(applicationContext, "Media captured.", Toast.LENGTH_SHORT).show()
                (AddPostFragment() as CallBack ).onResultRecived(media)
            }
        }
    }
}