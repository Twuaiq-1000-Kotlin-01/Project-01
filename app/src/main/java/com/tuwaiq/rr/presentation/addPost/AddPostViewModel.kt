package com.tuwaiq.rr.presentation.addPost

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.rr.domain.models.PostData
import com.tuwaiq.rr.domain.usecases.authUsecases.LoginUsecase
import com.tuwaiq.rr.domain.usecases.authUsecases.SignupUsecase
import com.tuwaiq.rr.domain.usecases.postUsecases.AddPostUsecase
import com.tuwaiq.rr.domain.usecases.postUsecases.UploadImgToStorageUsecase
import com.tuwaiq.rr.domain.usecases.profileUsecases.AddUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel  @Inject constructor(
    private val addPostUsecase: AddPostUsecase,
    private val uploadImgToStorageUseCase:UploadImgToStorageUsecase
): ViewModel() {

    fun addPost(postData: PostData){
        viewModelScope.launch(Dispatchers.IO) {
            addPostUsecase(postData)
        }
    }

    fun uploadImgToStorage(filename:String, uri: Uri): LiveData<String> =
        uploadImgToStorageUseCase(filename, uri)


}