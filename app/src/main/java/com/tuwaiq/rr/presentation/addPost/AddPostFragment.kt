package com.tuwaiq.rr.presentation.addPost

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
import com.tuwaiq.rr.databinding.AddPostFragmentBinding
import com.tuwaiq.rr.databinding.LoginFragmentBinding
import com.tuwaiq.rr.domain.models.PostData
import com.tuwaiq.rr.presentation.MainActivity
import com.tuwaiq.rr.presentation.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

lateinit var uri: Uri
var cameraClicked = false

@AndroidEntryPoint
class AddPostFragment : Fragment(), MainActivity.CallBack {


    private  val viewModel: AddPostViewModel by viewModels()
    private lateinit var binding: AddPostFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddPostFragmentBinding.inflate(layoutInflater)

        binding.postButton.setOnClickListener {
            val userId = Firebase.auth.currentUser?.uid.toString()
            val postText = binding.editText.text.toString()
            val photoId = UUID.randomUUID()
            var postPhoto = ""
            val filename = "$photoId"
            viewModel.uploadImgToStorage(filename, uri).observe(
                viewLifecycleOwner,{
                    postPhoto = it
                    val post = PostData(postPhoto=postPhoto, postText = postText, userId = userId)
                    viewModel.addPost(post)
                    findNavController().navigate(R.id.action_addPostFragment_to_homeFragment)
                }
            )
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cameraButton.setOnClickListener {
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
            binding.imageUri.setImageURI(uri)
        }
    }

    override fun onResultRecived(media: Media) {
        val path = media.path
        cameraClicked = true
        uri = Uri.fromFile(File(path))
    }
}