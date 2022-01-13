package com.tuwaiq.rr.data.remote

import com.tuwaiq.rr.domain.models.PostData

data class PostDataDto(
    var userId:String = "",
    var postText:String = "",
    var postPhoto:String = ""
) {

    fun toPostData():PostData{
        return PostData(
            userId = userId,
            postText = postText,
            postPhoto = postPhoto
        )
    }
}