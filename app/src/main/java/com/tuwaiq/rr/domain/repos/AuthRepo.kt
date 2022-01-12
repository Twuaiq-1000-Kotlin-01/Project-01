package com.tuwaiq.rr.domain.repos

interface AuthRepo {

    fun signup(email:String,password:String)

    fun login(email:String,password:String)

}