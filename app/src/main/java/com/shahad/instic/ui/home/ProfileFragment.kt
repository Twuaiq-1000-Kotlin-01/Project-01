package com.shahad.instic.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.shahad.instic.R
import com.shahad.instic.databinding.FragmentPostBinding
import com.shahad.instic.databinding.FragmentProfileBinding
import com.shahad.instic.ui.auth.AuthActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val username = auth.currentUser!!.displayName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUsername.text = username

        binding.signout.setOnClickListener {
            logoutButtonPressed()
        }

    }

    private fun logoutButtonPressed() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Logout")
        alertDialog.setMessage("Are you sure you want to logout?")

        alertDialog.setPositiveButton("Yes") { _, _ ->
            FirebaseAuth.getInstance().signOut()
            Intent(requireContext(), AuthActivity::class.java).also { startActivity(it) }
            activity?.finish()
        }

        alertDialog.setNeutralButton("Cancel") { _, _ ->
        }
        alertDialog.show()
    }

}