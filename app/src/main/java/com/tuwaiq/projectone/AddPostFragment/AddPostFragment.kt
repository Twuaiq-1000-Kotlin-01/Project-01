package com.tuwaiq.projectone.addPostFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.tuwaiq.projectone.R
import com.tuwaiq.projectone.databinding.AddPostFragmentBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val PICK_IMAGE = 1
class AddPostFragment : Fragment() {

    private var _binding: AddPostFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {  ViewModelProvider(this).get(AddPostViewModel::class.java)}

    private var url = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddPostFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            userPic()
        }
        binding.imagePicker.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        binding.cancelBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addPostFragment2_to_timeLineFragment2)
        }

        binding.postTv.setOnClickListener {
            if (!binding.editTextTextMultiLine.text.toString().isNullOrEmpty()) {
                lifecycleScope.launch {
                    updateUI()
                }
                Log.d("----- user add", "onActivityCreated:$it ")

            } else {
                Toast.makeText(requireContext(),"empty post isn't a meaningful post to share  ",Toast.LENGTH_LONG).show()
            }
        }

    }

    private suspend fun userPic() {
        binding.userPhotoIv.load(viewModel.getProfilePhoto())
    }

    private suspend fun updateUI(){
        viewModel.getUserInfo().onEach {
            viewModel.addPost(binding.editTextTextMultiLine.text.toString(),it,findNavController(), url)
        }.launchIn(lifecycleScope)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE) {
            if (data?.data != null) {
                binding.addPostIv.visibility = View.VISIBLE
                binding.addPostIv.setImageURI(data.data as Uri)
                viewModel.uploadPostPhoto(data.data as Uri)
                url = viewModel.uploadPostPhoto(data.data as Uri)
            }
        }
    }
}