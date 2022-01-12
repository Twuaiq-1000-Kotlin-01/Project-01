package com.tuwaiq.rr.presentation.auth

import androidx.lifecycle.ViewModel
import com.tuwaiq.rr.domain.usecases.authUsecases.LoginUsecase
import com.tuwaiq.rr.domain.usecases.authUsecases.SignupUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signupUseCase:SignupUsecase,
    private val loginUseCase:LoginUsecase
): ViewModel() {

    fun signup(email:String,password:String) =
        signupUseCase(email,password)

    fun login(email: String,password: String) =
        loginUseCase(email, password)

}