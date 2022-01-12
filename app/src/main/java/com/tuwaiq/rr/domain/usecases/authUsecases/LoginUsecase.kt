package com.tuwaiq.rr.domain.usecases.authUsecases

import com.tuwaiq.rr.domain.repos.AuthRepo
import javax.inject.Inject

class LoginUsecase  @Inject constructor(
    private val repo: AuthRepo
) {

    operator fun invoke(email:String,password:String) =
        repo.login(email, password)

}