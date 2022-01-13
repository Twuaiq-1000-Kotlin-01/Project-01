package com.tuwaiq.projectone.addPostFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.projectone.model.Post
import com.tuwaiq.projectone.model.User
import com.tuwaiq.projectone.repo.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

class AddPostViewModel : ViewModel() {
    private val repo: Repo = Repo.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    // val user = MutableLiveData<User>()

    fun addPost(postContent: String, user: User, navController: NavController, url: String) {
        viewModelScope.launch {
            val uniqueID = UUID.randomUUID().toString()
            val newPost = Post(
                uniqueID,
                postContent,
                auth.currentUser!!.uid,
                user.profilePhoto,
                user.name,
                user.username,
                url
            )
            repo.addPost(newPost, findNavController = navController)
        }
    }

    suspend fun getUserInfo(): Flow<User> {

        return repo.getUserInfo()

    }

    suspend fun getProfilePhoto(): String {
        return repo.getProfilePhoto()
    }

    fun uploadPostPhoto(uri: Uri): String {
        return repo.uploadPostPhoto(uri)
    }
}