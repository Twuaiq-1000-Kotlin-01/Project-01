package com.tuwaiq.rr.data.repos

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tuwaiq.rr.data.remote.PostDataDto
import com.tuwaiq.rr.domain.models.PostData
import com.tuwaiq.rr.domain.repos.PostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await

class PostRepoImpl:PostRepo {

    private val postCollectionRef: CollectionReference = Firebase.firestore.collection("posts")
    private val storageRef = Firebase.storage.reference


    override suspend fun addPost(postData: PostData) {
        postCollectionRef.add(postData).await()
    }

    override suspend fun getPost(): List<PostDataDto> {
        return postCollectionRef.get().await().toObjects(PostDataDto::class.java)
    }

    override fun uploadImgToStorage(filename: String, uri: Uri): LiveData<String> {
        return liveData(Dispatchers.IO) {
            val ref = storageRef.child("images/").child(filename)
            val uploadTask = ref.putFile(uri)
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.await()
            emit(urlTask.toString())
        }
    }



}