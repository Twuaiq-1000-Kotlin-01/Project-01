package com.tuwaiq.rr.data.remote

import com.tuwaiq.rr.domain.models.PostData

data class PostDataDto(
    var username:String = "",
    var userId:String = "",
    var postText:String = "",
    var postPhoto:String = ""
) {

    fun toPostData():PostData{
        return PostData(
            username = username,
            userId = userId,
            postText = postText,
            postPhoto = postPhoto
        )
    }
}