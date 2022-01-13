package com.shahad.instic.ui.model

import com.google.firebase.Timestamp

data class Post(
    val uid: String? = null,
    val username:String="",
    val text: String = "",
    var imagePath: String? = null,
    val postId:String="",
    val date: Timestamp = Timestamp.now()
)
