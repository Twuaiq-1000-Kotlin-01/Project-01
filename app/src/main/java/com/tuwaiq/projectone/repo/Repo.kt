package com.tuwaiq.projectone.repo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.projectone.R
import com.tuwaiq.projectone.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "Repo"
class Repo private constructor(val context: Context){

    val db = FirebaseFirestore.getInstance().collection("Users")

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
            var exists = false
            var un = ""
            db.get().addOnSuccessListener {
                it.forEach {
                    un = it.getString("username").toString()
                    if (un == username) {
                        exists = true
                    }
                }
            }.addOnCompleteListener {
                if (!exists) {
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val user = auth.currentUser
                                val users = User(name, username, email)
                                db.document(user!!.uid).set(users)
                                findNavController.navigate(R.id.action_registerFragment_to_loginFragment)
                            } else {
                                Log.e(TAG, it.exception.toString())
                            }
                        }
                } else {
                    //Snackbar.make(Window.getDefaultFeatures(context)., "This username already exists", Snackbar.LENGTH_SHORT).show()
                    Toast.makeText(context, "This username already exists", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "this username exists")
                }

            }
        }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    suspend fun name(): String? {
        return db.document("${FirebaseAuth.getInstance().currentUser?.uid}").get().await().getString("name")
    }

    suspend fun userName(): String? {
        return db.document("${FirebaseAuth.getInstance().currentUser?.uid}").get().await().getString("username")
    }

    suspend fun date(): String {

        return db.document("${FirebaseAuth.getInstance().currentUser?.uid}").get().await().getDate("date").toString()
    }

    fun addBio(bio: String) {
        db.document("${FirebaseAuth.getInstance().currentUser?.uid}").update("bio", bio)
    }

    suspend fun getBio(): String? {

        return db.document("${FirebaseAuth.getInstance().currentUser?.uid}").get().await().getString("bio")
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
