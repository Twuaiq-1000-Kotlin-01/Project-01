package com.tuwaiq.rr.presentation.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tuwaiq.rr.R
import com.tuwaiq.rr.databinding.ActivityMainBinding
import com.tuwaiq.rr.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private  val viewModel:AuthViewModel by viewModels()
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {
            val email = binding.emailInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()
            viewModel.login(email,password)
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        return binding.root
    }

}