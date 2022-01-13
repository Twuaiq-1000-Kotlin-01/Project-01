package com.tuwaiq.rr.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.rr.R
import com.tuwaiq.rr.data.repos.completed
import com.tuwaiq.rr.databinding.SignupFragmentBinding
import com.tuwaiq.rr.domain.models.UserData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private  val viewModel:AuthViewModel by viewModels()
    private lateinit var binding: SignupFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignupFragmentBinding.inflate(layoutInflater)

        binding.signupButton.setOnClickListener {

            val email = binding.emailInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()
            viewModel.signup(email, password)
            if (completed){
                val fullName = binding.nameInputEditText.text.toString()
                val username = binding.usernameInputEditText.text.toString()
                val userId = Firebase.auth.currentUser?.uid.toString()
                val userData = UserData(fullName,username,userId)
                viewModel.addUser(userData).also {
                    findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
                }
            }
        }

        binding.backBtn.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.LogInText.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        return binding.root
    }

}