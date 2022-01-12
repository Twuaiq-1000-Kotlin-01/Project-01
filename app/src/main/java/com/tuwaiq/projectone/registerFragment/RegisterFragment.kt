package com.tuwaiq.projectone.registerFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.tuwaiq.projectone.R
import com.tuwaiq.projectone.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.loginHereTv.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.regBtn.setOnClickListener{
            val name = binding.regNameTv.text.toString()
            val username = binding.regUsernameTv.text.toString()
            val email = binding.regEmailTv.text.toString()
            val pass = binding.regPasswordTv.text.toString()

            when{
                name.isEmpty() -> showToast("Please enter a name")
                username.isEmpty() -> showToast("Please enter a username")
                email.isEmpty() -> showToast("Please enter an email")
                pass.isEmpty() -> showToast("Please enter a password")
                else -> {
                    registerViewModel.register(name, username, email, pass, findNavController())
                    //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}