package com.tuwaiq.projectone.profileFragment

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.projectone.model.Post
import com.tuwaiq.projectone.model.User
import com.tuwaiq.projectone.repo.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TAG = "ProfileViewModel"
class ProfileViewModel : ViewModel() {

    private val repo = Repo.getInstance()

    fun userInfo(): Flow<User> {
        return repo.userInfo()
    }
    fun addBio(bio: String) {
        repo.addBio(bio)
    }
    fun logout() {
        repo.logout()
    }
    fun uploadPhoto(uri: Uri) {
        repo.uploadPhoto(uri)
    }
    suspend fun getAllPosts(): Flow<MutableList<Post>> {
        return repo.getPosts()
    }
}