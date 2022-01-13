package com.shahad.instic.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.shahad.instic.databinding.FragmentProfileBinding
import com.shahad.instic.ui.InsticViewModel
import com.shahad.instic.ui.auth.AuthActivity
import com.shahad.instic.utils.initializeAlbumLib
import com.yanzhenjie.album.Album
import java.io.File

class ProfileFragment : Fragment() {
	
	private lateinit var binding: FragmentProfileBinding
	private val viewModel by viewModels<InsticViewModel>()
	private val auth = FirebaseAuth.getInstance()
	private val username = auth.currentUser!!.displayName
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentProfileBinding.inflate(inflater, container, false)
		
		Glide.with(this)
			.load(auth.currentUser!!.photoUrl)
			.centerCrop()
			.into(binding.imgprofile)
		
		initializeAlbumLib(requireContext())
		
		binding.imgprofile.setOnClickListener {
			Toast.makeText(requireContext(), "Clicked on ${it.id}", Toast.LENGTH_SHORT).show()
			Album.album(requireContext())
				.singleChoice()
				.camera(false)
				.filterMimeType { mimeType ->
					mimeType.equals("jpeg", true) || mimeType.equals("png", true)
				}
				.afterFilterVisibility(true)
				.onResult { albumFiles ->
					val uri = File(albumFiles[0].path).toUri()
					viewModel.updateUserProfile(photoUri = uri)
				}
				.onCancel { cancelMessage ->
					Toast.makeText(activity, cancelMessage, Toast.LENGTH_SHORT).show()
				}
				.start()
		}
		
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