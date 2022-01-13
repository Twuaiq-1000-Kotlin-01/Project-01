package com.tuwaiq.rr.presentation.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.rr.R
import com.tuwaiq.rr.databinding.HomeFragmentBinding
import com.tuwaiq.rr.databinding.ProfileFragmentBinding
import com.tuwaiq.rr.domain.models.UserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private  val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(layoutInflater)

        var user = UserData()
        lifecycleScope.launch {
            val userId = Firebase.auth.currentUser?.uid.toString()
            viewModel.getUser(userId).observe(
                viewLifecycleOwner,{
                    user =it
                    binding.nameText.text = user.userFullName
                    binding.usernameText.text = "@${user.username}"
                    binding.descriptionText.text = user.userDesc
                    Glide.with(this@ProfileFragment)
                        .load(user.userPhoto)
                        .into(binding.profileImageView)
                }
            )
        }

        binding.logOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)


        }

        return binding.root
    }


}