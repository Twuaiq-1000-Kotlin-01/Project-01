package com.tuwaiq.projectone.loginFragment

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.tuwaiq.projectone.repo.Repo

class LoginViewModel : ViewModel() {

    private val repo = Repo.getInstance()

    fun login(email: String, password: String, findNavController: NavController) {
        repo.login(email, password, findNavController)
    }
}