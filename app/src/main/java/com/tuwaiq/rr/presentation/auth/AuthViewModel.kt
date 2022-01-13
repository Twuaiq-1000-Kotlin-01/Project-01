package com.tuwaiq.rr.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.rr.domain.models.UserData
import com.tuwaiq.rr.domain.usecases.authUsecases.LoginUsecase
import com.tuwaiq.rr.domain.usecases.authUsecases.SignupUsecase
import com.tuwaiq.rr.domain.usecases.profileUsecases.AddUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signupUseCase:SignupUsecase,
    private val loginUseCase:LoginUsecase,
    private val addUserUsecase: AddUserUsecase
): ViewModel() {

    fun signup(email:String,password:String) =
        signupUseCase(email,password)

    fun login(email: String,password: String) =
        loginUseCase(email, password)

    fun addUser(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            addUserUsecase(userData)
        }
    }

}