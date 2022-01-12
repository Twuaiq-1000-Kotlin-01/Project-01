package com.tuwaiq.projectone.loginFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.projectone.R
import com.tuwaiq.projectone.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            //findNavController().navigate(R.id.action_loginFragment_to_timeLineFragment2)
        }

        binding.registerHereTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmailTv.text.toString()
            val password = binding.loginPasswordTv.text.toString()

            when{
                email.isEmpty() -> showToast("Enter your email")
                password.isEmpty() -> showToast("Enter your password")
                else -> {
                    loginViewModel.login(email, password, findNavController())

                    //findNavController().navigate(R.id.action_loginFragment_to_timeLineFragment2)
                }
            }
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

}