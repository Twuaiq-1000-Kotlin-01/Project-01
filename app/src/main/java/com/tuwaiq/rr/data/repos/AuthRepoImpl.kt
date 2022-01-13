package com.tuwaiq.rr.data.repos

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.rr.domain.repos.AuthRepo


private lateinit var auth: FirebaseAuth
private const val TAG = "AuthRepoImpl"
var completed = false
class AuthRepoImpl: AuthRepo {
    override fun signup(email: String, password: String) {

        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    Log.d(TAG, "createUserWithEmail:success")

                    completed = true
                }else{
                    Log.e(TAG,"something goose wrong")
                }
            }

    }

    override fun login(email: String, password: String) {

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                } else {
                    Log.e(TAG, "something goose wrong")
                }
            }
    }

}