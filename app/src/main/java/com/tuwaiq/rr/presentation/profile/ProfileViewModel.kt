package com.tuwaiq.rr.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tuwaiq.rr.domain.models.UserData
import com.tuwaiq.rr.domain.usecases.postUsecases.GetPostUsecase
import com.tuwaiq.rr.domain.usecases.profileUsecases.GetUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUsecase: GetUserUsecase
): ViewModel(){

    fun getUser(userId:String): LiveData<UserData> {
        return liveData {
            emit(getUserUsecase(userId))
        }
    }
}