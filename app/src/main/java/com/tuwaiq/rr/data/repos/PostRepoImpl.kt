package com.tuwaiq.rr.data.repos

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.rr.domain.models.PostData
import com.tuwaiq.rr.domain.repos.PostRepo
import kotlinx.coroutines.tasks.await

class PostRepoImpl:PostRepo {

    private val postCollectionRef: CollectionReference = Firebase.firestore.collection("posts")


    override suspend fun addPost(postData: PostData) {
        postCollectionRef.add(postData).await()
    }

}