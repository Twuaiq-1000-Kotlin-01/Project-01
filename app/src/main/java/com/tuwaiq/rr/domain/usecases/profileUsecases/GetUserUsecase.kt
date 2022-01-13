package com.tuwaiq.rr.domain.usecases.profileUsecases

import com.tuwaiq.rr.domain.models.UserData
import com.tuwaiq.rr.domain.repos.ProfileRepo
import javax.inject.Inject

class GetUserUsecase @Inject constructor(
    private val profileRepo: ProfileRepo
) {

    suspend operator fun invoke(userId:String): UserData {
        return profileRepo.getUser(userId).toUserData()

    }

}