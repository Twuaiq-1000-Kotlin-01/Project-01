package com.tuwaiq.projectone.model

import java.util.*

data class User(
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var bio: String = "",
    var date: Date = Date()
)