package com.tuwaiq.projectone.profileFragment

import androidx.lifecycle.ViewModel
import com.tuwaiq.projectone.repo.Repo
import kotlinx.coroutines.flow.Flow

class ProfileViewModel : ViewModel() {

    private val repo = Repo.getInstance()

    suspend fun name(): String? {
        return repo.name()
    }
    suspend fun username(): String? {
        return repo.userName()
    }
    suspend fun date(): String {
        return repo.date()
    }
    fun addBio(bio: String) {
        repo.addBio(bio)
    }
    suspend fun getBio(): String? {
        return repo.getBio()
    }
    fun logout() {
        repo.logout()
    }
}