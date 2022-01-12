package com.shahad.instic.ui.model

import com.google.firebase.Timestamp

data class Post(
    val uid: String = "",
    val text: String = "",
    val imagePath: String = "",
    val date: Timestamp = Timestamp.now()
)
