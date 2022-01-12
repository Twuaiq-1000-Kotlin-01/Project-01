package com.shahad.instic.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
            if (!post.imagePath.isNullOrEmpty()) {
                Glide.with(itemBinding.root.context)
                    .load(post.imagePath)
                    .into(itemBinding.imgviewPostImage)
                Log.i("ItemAdapter", "bind: ${post.imagePath}")
            }
//            itemBinding.imgviewPostImage.load(post.imagePath)
            itemBinding.tvPostText.text = post.text
            itemBinding.tvUsername.text = post.username
        }

    }
}