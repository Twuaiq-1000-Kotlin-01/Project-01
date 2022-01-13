package com.shahad.instic.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahad.instic.databinding.FragmentCommentBinding
import com.shahad.instic.ui.MainViewModel

const val POST_ID = "POST_ID"

class CommentFragment : AppCompatActivity() {

    private lateinit var binding: FragmentCommentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var postId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras


        if (extras != null) {
            postId = extras.getString(POST_ID).toString()
        }

        binding = FragmentCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val recyclerview: RecyclerView = binding.rv
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        recyclerview.layoutManager = LinearLayoutManager(this)

        viewModel.getComments(postId).observe(this) {
            recyclerview.adapter = CommentAdapter(it)
        }

    }
}