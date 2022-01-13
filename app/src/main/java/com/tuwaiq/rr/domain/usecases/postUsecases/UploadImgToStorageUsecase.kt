package com.tuwaiq.rr.domain.usecases.postUsecases

import android.net.Uri
import androidx.lifecycle.LiveData
import com.tuwaiq.rr.domain.repos.PostRepo
import javax.inject.Inject

class UploadImgToStorageUsecase @Inject constructor(
    private val postRepo: PostRepo
) {

    operator fun invoke(filename:String,uri: Uri): LiveData<String> =
        postRepo.uploadImgToStorage(filename,uri)

}