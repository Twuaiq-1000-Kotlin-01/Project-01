package com.tuwaiq.projectone.Repo

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.projectone.R
import com.tuwaiq.projectone.model.User
import java.lang.Exception

private const val TAG = "Repo"
class Repo private constructor(context: Context){

        fun login(email: String, password: String, findNavController: NavController, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        findNavController.navigate(R.id.action_loginFragment_to_timeLineFragment2)
                    else {
                        Log.e(TAG, it.exception.toString())
                    }
                }
            }

        fun register(name: String, username: String, email: String, pass: String, findNavController: NavController, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        val users = User(name, username, email)
                        val db = FirebaseFirestore.getInstance()
                        db.collection("Users")
                            .document(user!!.uid).set(users)
                        findNavController.navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Log.e(TAG, it.exception.toString())
                    }
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
