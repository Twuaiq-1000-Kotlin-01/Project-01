package com.shahad.instic.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.shahad.instic.ui.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val uId = auth.currentUser!!.uid
    private val collection = db.collection("post")
    private var postsList = mutableListOf<Post>()

    fun newPost(text: String, imagePath: String) {
        val post = Post(uId,auth.currentUser!!.displayName.toString(), text, imagePath)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                collection.document().set(post).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                    } else {
                        Log.e("newPost failed", task.exception?.message.toString())
                    }
                }

            }
        }
    }


    fun getPosts(): MutableLiveData<List<Post>> {
        var livePostsList = MutableLiveData<List<Post>>()
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