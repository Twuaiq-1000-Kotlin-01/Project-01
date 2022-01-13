package com.shahad.instic.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.shahad.instic.R
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
			itemBinding.tvPostText.text = post.text
			itemBinding.tvUsername.text = post.username
			if (post.imagePath.isNullOrEmpty()) {
				itemBinding.imgviewPostImage.visibility = View.GONE
				return
			}
			
			val storage = FirebaseStorage.getInstance()
			val storageRef = storage.getReferenceFromUrl("gs://instic-5f862.appspot.com/uploads")
			val pathReference = storageRef.child("${post.uid}")
			pathReference
				.downloadUrl
				.addOnSuccessListener {
					Log.d("PostAdapter", "downloadUrl: $it")
					Glide.with(itemBinding.root.context)
						.load(it)
						.centerCrop()
						.placeholder(R.drawable.baseline_crop_original_teal_700_24dp)
						.error(R.drawable.twotone_error_red_800_24dp)
						.into(itemBinding.imgviewPostImage)
				}
				.addOnFailureListener {
					Log.e("ItemAdapter", "bind: ${it.cause} -> ${post.uid}")
				}
		}
		
	}
}