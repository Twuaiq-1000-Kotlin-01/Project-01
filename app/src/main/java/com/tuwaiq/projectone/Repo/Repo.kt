package com.tuwaiq.projectone.Repo

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.projectone.model.User
import java.lang.Exception

class Repo private constructor(context: Context){

        fun login(email: String, password: String, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        return@addOnCompleteListener
                    } else {
                        throw Exception("wrong login")
                    }
                }
        }

        fun register(name: String, email: String, pass: String, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    val user = auth.currentUser
                    val users = User(name, email)
                    val db = FirebaseFirestore.getInstance()
                    db.collection("Users")
                        .document(user!!.uid).set(users)
                    return@addOnSuccessListener
                }.addOnFailureListener {
                    return@addOnFailureListener
                }
        }

    companion object{
        private var INSTANCE:Repo? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = Repo(context)
            }
        }

        fun getInstance():Repo = INSTANCE ?: throw IllegalStateException("Initialize your repo first")
    }
}
