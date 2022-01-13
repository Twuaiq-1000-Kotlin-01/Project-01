package com.shahad.instic.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shahad.instic.databinding.RecyclerviewItemBinding
import com.shahad.instic.ui.model.Comment

class CommentAdapter(private val commentList: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.ItemAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ItemAdapter {
        val bind: RecyclerviewItemBinding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemAdapter(bind)
    }

    override fun onBindViewHolder(holder: ItemAdapter, position: Int) {
        holder.bind(commentList[position])

    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    inner class ItemAdapter(private val itemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(comment: Comment) {
            itemBinding.tvText.text = comment.text
            itemBinding.tvUsername.text = comment.username
            itemBinding.imgviewComment.visibility = View.GONE
            itemBinding.imgviewPostImage.visibility = View.GONE

        }
    }
}