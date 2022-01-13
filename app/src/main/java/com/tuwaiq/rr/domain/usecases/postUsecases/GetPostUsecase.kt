package com.tuwaiq.rr.domain.usecases.postUsecases

import com.tuwaiq.rr.data.remote.PostDataDto
import com.tuwaiq.rr.domain.models.PostData
import com.tuwaiq.rr.domain.repos.PostRepo
import javax.inject.Inject

class GetPostUsecase @Inject constructor(
    private val postRepo:PostRepo
) {

    suspend operator fun invoke():List<PostData>{
        val list = postRepo.getPost()
        val postList = mutableListOf<PostDataDto>()
        list.forEach {
            val postData = it
            postList += postData
        }
        return postList.map { it.toPostData() }
    }

}