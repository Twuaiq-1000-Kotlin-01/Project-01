package com.shahad.instic.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shahad.instic.databinding.RecyclerviewItemBinding
import com.shahad.instic.ui.model.Post

class PostAdapter(private val postList: List<Post>) :
    RecyclerView.Adapter<PostAdapter.ItemAdapter>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter {
        val bind: RecyclerviewItemBinding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemAdapter(bind)
    }

    override fun onBindViewHolder(holder: ItemAdapter, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    inner class ItemAdapter(val itemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(post: Post) {
            itemBinding.imgviewPostImage.load(post.imagePath)
            itemBinding.tvPostText.text = post.text
            itemBinding.tvUsername.text = post.username
        }

    }
}