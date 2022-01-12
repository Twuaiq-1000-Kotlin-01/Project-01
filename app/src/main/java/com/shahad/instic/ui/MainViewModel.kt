package com.shahad.instic.ui

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.storage.FirebaseStorage
import com.shahad.instic.ui.model.Post
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class MainViewModel : ViewModel() {
	private val auth = FirebaseAuth.getInstance()
	private val db = FirebaseFirestore.getInstance()
	private val uId = auth.currentUser!!.uid
	private val collection = db.collection("post")
	private var postsList = mutableListOf<Post>()
	
	fun newPost(text: String, imagePath: String?) = Completable.create { emitter ->
		val randomUuid = UUID.randomUUID().toString()
		val post = Post(uId, auth.currentUser!!.displayName.toString(), text, randomUuid)
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				collection.document().set(post).addOnCompleteListener { task ->
					if (task.isSuccessful) {
						if (imagePath != null) {
							uploadImage(imagePath, randomUuid, emitter)
						} else {
							emitter.onComplete()
						}
					} else {
						Log.e("newPost failed", task.exception?.message.toString())
					}
				}
			}
		}
	}
	
	private fun uploadImage(path: String, uuid: String, emitter: CompletableEmitter) {
		val storage = FirebaseStorage.getInstance()
		val storageRef = storage.reference
		val imageRef = storageRef.child("uploads/$uuid")
		val uri = File(path).toUri()
		imageRef.putFile(uri)
			.addOnSuccessListener {
				Log.i("MainViewModel", "uploadImage: Uploaded $uuid")
				emitter.onComplete()
			}
			.addOnFailureListener {
				emitter.onError(it)
				Log.e("MainViewModel", "uploadImage: ${it.message}", it)
			}
	}
	
	fun getPosts(): MutableLiveData<List<Post>> {
		val livePostsList = MutableLiveData<List<Post>>()
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				collection.orderBy("date")
					.addSnapshotListener(object : EventListener<QuerySnapshot> {
						override fun onEvent(
							value: QuerySnapshot?,
							error: FirebaseFirestoreException?
						) {
							if (error != null) {
								Log.e("getPosts failed", error.message.toString())
								return
							}
							
							for (dc: DocumentChange in value?.documentChanges!!) {
								if (dc.type == DocumentChange.Type.ADDED) {
									postsList.add(dc.document.toObject(Post::class.java))
								}
							}
							livePostsList.postValue(postsList)
						}
					})
			}
		}
		return livePostsList
	}
}