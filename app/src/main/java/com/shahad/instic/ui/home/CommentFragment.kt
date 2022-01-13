package com.shahad.instic.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahad.instic.databinding.FragmentCommentBinding
import com.shahad.instic.ui.MainViewModel

private const val POST_ID = "POST_ID"

class CommentFragment : Fragment() {

    private lateinit var binding :FragmentCommentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var postId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        arguments?.let {
            postId = it.getString(POST_ID,"")
        }
            binding = FragmentCommentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview: RecyclerView = binding.rv
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getComments(postId) .observe(viewLifecycleOwner) {
            recyclerview.adapter =CommentAdapter(it)
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(postId: String) =
                CommentFragment().apply {
                    arguments = Bundle().apply {
                        putString(POST_ID, postId)
                    }
                }
    }
}