package com.tuwaiq.projectone.profileFragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.DialogCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tuwaiq.projectone.R
import com.tuwaiq.projectone.databinding.ProfileFragmentBinding
import com.tuwaiq.projectone.databinding.RegisterFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import android.content.Intent
import android.net.Uri

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.projectone.model.Post
import com.tuwaiq.projectone.timeLineFragment.TimeLineAdapter


private const val TAG = "ProfileFragment"
private const val PICK_IMAGE = 1
class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel by lazy { ViewModelProvider(this).get(ProfileViewModel::class.java) }

    private var uri: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            userInfo()
        }

        binding.userTimeLine.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            updateUI()
        }

        binding.bioEt.setOnClickListener {
            binding.doneBioIb.visibility = View.VISIBLE
            binding.bioEt.isCursorVisible = true
            binding.bioEt.isFocusableInTouchMode = true
            binding.bioEt.inputType = InputType.TYPE_CLASS_TEXT
            binding.bioEt.requestFocus()
        }

        binding.doneBioIb.setOnClickListener {
            binding.doneBioIb.visibility = View.INVISIBLE

            binding.bioEt.isCursorVisible = false
            binding.bioEt.isLongClickable = false
            binding.bioEt.isClickable = false
            binding.bioEt.keyListener = null
            binding.bioEt.isSelected = false
            binding.bioEt.isFocusableInTouchMode = false
            binding.bioEt.inputType = InputType.TYPE_NULL
            binding.bioEt.setBackgroundResource(android.R.color.transparent)
            profileViewModel.addBio(binding.bioEt.text.toString())
        }

        binding.profileUserPhotoIv.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        binding.logoutIb.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    profileViewModel.logout()
                    findNavController().navigate(R.id.action_profileFragment2_to_loginFragment)
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create().show()
        }
    }

    private fun userInfo() {
        profileViewModel.userInfo().onEach {
            Glide.with(requireContext())
                .load(it.profilePhoto)
                .into(binding.profileUserPhotoIv)
            binding.profileNameTv.text = it.name
            binding.profileUsernameTv.text = "@ ${it.username}"
            binding.bioEt.setBackgroundResource(android.R.color.transparent)
            binding.bioEt.setText(it.bio)
            var spf = SimpleDateFormat("E LLL dd hh:mm:ss z yyyy")
            val parsed = spf.parse(it.date.toString())
            spf = SimpleDateFormat("LLLL, yyyy")
            val formatted = spf.format(parsed)
            binding.joinedDateTv.text = "Joined $formatted"
        }.launchIn(lifecycleScope)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE) {

            uri = data?.data as Uri
            binding.profileUserPhotoIv.setImageURI(uri)
            profileViewModel.uploadPhoto(uri)
            //profileViewModel.addProfilePhoto()
        }
    }

    private suspend fun updateUI(){
        profileViewModel.getAllPosts().onEach {
            val a = it.filter {post ->
                post.postOwnerID == FirebaseAuth.getInstance().currentUser?.uid
            }
            binding.userTimeLine.adapter = ProfileAdapter(a as MutableList<Post>)
            Log.d(TAG, "list: $a")

        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}