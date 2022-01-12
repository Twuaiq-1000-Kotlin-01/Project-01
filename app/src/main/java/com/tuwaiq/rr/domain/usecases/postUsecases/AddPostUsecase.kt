package com.tuwaiq.rr.domain.usecases.postUsecases

import com.tuwaiq.rr.domain.models.PostData
import com.tuwaiq.rr.domain.repos.PostRepo
import javax.inject.Inject

class AddPostUsecase @Inject constructor(
    private val postRepo : PostRepo
) {

    suspend operator fun invoke(postData: PostData)=
        postRepo.addPost(postData)


}