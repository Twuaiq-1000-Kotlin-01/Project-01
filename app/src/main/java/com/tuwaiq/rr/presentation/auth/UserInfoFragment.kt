package com.tuwaiq.rr.presentation.auth

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sandrios.sandriosCamera.internal.SandriosCamera
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration
import com.sandrios.sandriosCamera.internal.ui.model.Media
import com.tuwaiq.rr.R
import com.tuwaiq.rr.databinding.SignupFragmentBinding
import com.tuwaiq.rr.databinding.UserInfoFragmentBinding
import com.tuwaiq.rr.domain.models.PostData
import com.tuwaiq.rr.domain.models.UserData
import com.tuwaiq.rr.presentation.MainActivity
import com.tuwaiq.rr.presentation.addPost.cameraClicked
import com.tuwaiq.rr.presentation.addPost.uri
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UserInfoFragment : Fragment() , MainActivity.CallBack {

    private  val viewModel:AuthViewModel by viewModels()
    private lateinit var binding: UserInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserInfoFragmentBinding.inflate(layoutInflater)

        binding.saveButton.setOnClickListener {
            val name = binding.nameInputEditText.text.toString()
            val username = binding.usernameInputEditText.text.toString()
            val desc = binding.descriptionInputEditText.text.toString()
            val userId = Firebase.auth.currentUser?.uid.toString()
            val userPhoto = uri.toString()
            val filename = "$username-${userId.toString()}"
            binding.saveButton.setOnClickListener {
                viewModel.uploadImgToStorage(filename, uri).observe(
                    viewLifecycleOwner,{
                        val user = UserData(userFullName = name, username = username, userId = userId,
                        userPhoto = userPhoto, userDesc = desc)
                        viewModel.addUser(user)
                        findNavController().navigate(R.id.action_userInfoFragment_to_homeFragment)
                    }
                )
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileImageView.setOnClickListener {
            showCamera()
        }
    }
    private fun showCamera(){
        SandriosCamera
            .with()
            .setShowPicker(true)
            .setVideoFileSize(20)
            .setMediaAction(CameraConfiguration.MEDIA_ACTION_BOTH)
            .enableImageCropping(true)
            .launchCamera(activity)
    }

    override fun onStart() {
        super.onStart()
        if(cameraClicked){
            binding.profileImageView.setImageURI(uri)
        }
    }

    override fun onResultRecived(media: Media) {
        val path = media.path
        cameraClicked = true
        uri = Uri.fromFile(File(path))
    }


}