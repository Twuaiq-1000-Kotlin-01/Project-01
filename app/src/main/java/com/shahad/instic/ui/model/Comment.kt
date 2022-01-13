package com.shahad.instic.ui.model

import com.google.firebase.Timestamp

data class Comment(
    val userId: String = "",
    val username: String = "",
    val text: String = "",
    val date: Timestamp = Timestamp.now()
)