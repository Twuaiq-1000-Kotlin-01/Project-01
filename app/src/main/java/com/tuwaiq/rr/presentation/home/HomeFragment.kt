package com.tuwaiq.rr.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.rr.R
import com.tuwaiq.rr.databinding.HomeFragmentBinding
import com.tuwaiq.rr.databinding.PostItemBinding
import com.tuwaiq.rr.domain.models.PostData
import com.tuwaiq.rr.domain.models.UserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private  val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: HomeFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.getPost().observe(
                this@HomeFragment, Observer { posts ->
                    binding.homeRecyclerView.adapter = PostsAdapter(posts)
                }
            )
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater)

        if (Firebase.auth.currentUser?.uid == null){
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }


    private inner class PostsHolder(val binding:PostItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(post: PostData){
            var user = UserData()
            lifecycleScope.launch {
                viewModel.getUser(post.userId).observe(
                    viewLifecycleOwner,{
                        user =it
                        binding.nameText.text = user.userFullName
                        binding.usernameText.text = "@${user.username}"
                        Glide.with(this@HomeFragment)
                            .load(user.userPhoto)
                            .into(binding.profileImageView)
                    }
                )
            }
            binding.postText.text = post.postText

            Glide.with(this@HomeFragment)
                .load(post.postPhoto)
                .into(binding.postImageView)


        }
    }

    private inner class PostsAdapter(val posts:List<PostData>):
        RecyclerView.Adapter<PostsHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsHolder {
            val binding = PostItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return PostsHolder(binding)
        }

        override fun onBindViewHolder(holder: PostsHolder, position: Int) {
            val post = posts[position]
            holder.bind(post)
        }

        override fun getItemCount(): Int = posts.size

    }




}