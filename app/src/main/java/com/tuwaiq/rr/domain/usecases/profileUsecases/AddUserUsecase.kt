package com.tuwaiq.rr.domain.usecases.profileUsecases

import android.service.autofill.UserData
import com.tuwaiq.rr.domain.repos.PostRepo
import com.tuwaiq.rr.domain.repos.ProfileRepo
import javax.inject.Inject

class AddUserUsecase @Inject constructor(
    private val profileRepo: ProfileRepo
) {

    suspend operator fun invoke(userData: UserData) =
        profileRepo.addUser(userData)

}