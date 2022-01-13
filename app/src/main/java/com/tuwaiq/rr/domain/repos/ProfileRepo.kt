package com.tuwaiq.rr.domain.repos

import android.service.autofill.UserData
import com.tuwaiq.rr.data.remote.UserDataDto

interface ProfileRepo {

    suspend fun addUser(userData: UserData)

    suspend fun getUser(userId:String): UserDataDto

}