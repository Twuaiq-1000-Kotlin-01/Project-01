package com.tuwaiq.rr.domain.repos

import android.net.Uri
import androidx.lifecycle.LiveData
import com.tuwaiq.rr.data.remote.PostDataDto
import com.tuwaiq.rr.domain.models.PostData

interface PostRepo {

    suspend fun addPost(postData: PostData)

}