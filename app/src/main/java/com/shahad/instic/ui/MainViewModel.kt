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
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class MainViewModel : ViewModel() {
	val disposables = CompositeDisposable()
	private val auth = FirebaseAuth.getInstance()
	private val db = FirebaseFirestore.getInstance()
//	private val uId = auth.currentUser?.uid
	private val collection = db.collection("post")
	private var postsList = mutableListOf<Post>()
	
	fun newPost(content: String, imagePath: String?) = Completable.create { emitter ->
		val randomUuid = UUID.randomUUID().toString()
		val displayName = auth.currentUser!!.displayName.toString()
		val post = Post(randomUuid, displayName, content, imagePath)
		
		val disposable = uploadImage(imagePath, randomUuid)
			.subscribe({
				post.imagePath = it
				viewModelScope.launch {
					withContext(Dispatchers.IO) {
						collection.document().set(post).addOnCompleteListener { task ->
							if (task.isSuccessful) {
								Log.i("MainViewModel", "newPost: success")
								emitter.onComplete()
							} else {
								emitter.onError(task.exception!!)
								Log.e("MainViewModel", "newPost: ${task.exception!!.message}", task.exception)
							}
						}
					}
				}
			}, {
				emitter.onError(it)
				Log.e("MainViewModel", "newPost: ${it.message}", it)
			})
		
		disposables.add(disposable)
	}
	
	private fun uploadImage(path: String?, uuid: String) = Observable.create<String> { emitter ->
		if (path == null) {
			Log.i("MainViewModel", "uploadImage: no image")
			emitter.onNext("")
			emitter.onComplete()
			return@create
		}
		
		val storage = FirebaseStorage.getInstance()
		val storageRef = storage.reference
		val imageRef = storageRef.child("uploads/$uuid")
		val uri = File(path).toUri()
		imageRef.putFile(uri)
			.addOnSuccessListener {
				Log.i("MainViewModel", "uploadImage: Uploaded $uuid [url: ${imageRef.downloadUrl}]")
				emitter.onNext(imageRef.downloadUrl.toString())
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
	
	override fun onCleared() {
		super.onCleared()
		disposables.dispose()
	}
}