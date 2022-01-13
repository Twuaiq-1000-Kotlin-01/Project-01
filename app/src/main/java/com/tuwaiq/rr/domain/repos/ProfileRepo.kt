package com.tuwaiq.rr.domain.repos

import com.tuwaiq.rr.data.remote.UserDataDto
import com.tuwaiq.rr.domain.models.UserData

interface ProfileRepo {

    suspend fun addUser(userData: UserData)

    suspend fun getUser(userId:String): UserDataDto

}