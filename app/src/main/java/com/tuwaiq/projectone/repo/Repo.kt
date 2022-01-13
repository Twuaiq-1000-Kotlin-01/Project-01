package com.tuwaiq.projectone.repo

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.tuwaiq.projectone.R
import com.tuwaiq.projectone.model.Post
import com.tuwaiq.projectone.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "Repo"
class Repo private constructor(val context: Context){

    var picUrl = ""

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

    fun userInfo(): Flow<User> {
        var user = User()
        return flow {
            db.addSnapshotListener { value, error ->
                value?.let {
                    for (doc in value) {
                        user = doc.toObject(User::class.java)
                        Log.d(TAG, "name: $user")
                    }

                }
            }
            delay(300)
           //Log.d(TAG, "name: $user")
            emit(user)
        }
    }

    fun addBio(bio: String) {
        db.document("${FirebaseAuth.getInstance().currentUser?.uid}").update("bio", bio)
    }

    fun addPost(
        post: Post,
        firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
        findNavController: NavController
    ) {
        firestore.collection("Posts")
            .document(post.postId).set(post).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "successful addPost: ${it.isSuccessful}")
                    findNavController.navigate(R.id.action_addPostFragment2_to_timeLineFragment2)
                } else {
                    Log.d(TAG, "failed addPost: ${it.isSuccessful}")
                }
            }
    }

    suspend fun getPosts(firestore: FirebaseFirestore = FirebaseFirestore.getInstance()): Flow<MutableList<Post>> {

        return flow {
            //    val allPosts = ArrayList<Post>()
            val a = firestore.collection("Posts").get().await()
            val postOBJ = a.toObjects(Post::class.java)
            emit(postOBJ)
        }
    }

    suspend fun getUserInfo(
        auth:FirebaseAuth = FirebaseAuth.getInstance(),
        firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    ): Flow<User> {

        return flow {
            val user= firestore.collection("Users").document(auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            emit(user)
        }
    }

    fun uploadPhoto(uri: Uri) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val storageRef = Firebase.storage.reference.child("$uid/${Calendar.getInstance().time}")
        val pic = storageRef.putFile(uri)
        pic.continueWithTask {task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                picUrl = it.result.toString()
                Log.d(TAG, "pic: $picUrl")
                    db.document("${FirebaseAuth.getInstance().currentUser?.uid}").update("profilePhoto", picUrl)

            }
        }
    }

    fun uploadPostPhoto(uri: Uri): String {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val storageRef = Firebase.storage.reference.child("$uid/posts/$${Calendar.getInstance().time}")
        val pic = storageRef.putFile(uri)
        pic.continueWithTask {task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                picUrl = it.result.toString()
                Log.d(TAG, "pic: $picUrl")
                //db.document("${FirebaseAuth.getInstance().currentUser?.uid}").update("profilePhoto", picUrl)
            }
        }
        return picUrl
    }

    suspend fun getProfilePhoto(): String {
        var a = ""
        db.document("${FirebaseAuth.getInstance().currentUser?.uid}").get()
            .addOnCompleteListener {
                a = it.result?.getString("profilePhoto").toString()
            }
        delay(500)
        return a
    }


    suspend fun getLikesPosts(
        auth: FirebaseAuth = FirebaseAuth.getInstance(),
        firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    ): Flow<String> {
        var likesPosts:String=""
        return flow {
            firestore.collection("Users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
                if(it != null){
                    likesPosts= it.getString("likes")!!

                }

            }
            emit(likesPosts)
        }


    }

    suspend fun getLikesCount(
        docId:String,
        firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    ): Flow<Int> {
        return flow {
            val value = firestore.collection("Posts").document(docId).get().await()
            val count= value.get("likesCount") as Int
            Log.d(TAG, "getLikesPosts: $count")
            emit(count)
        }
    }

    fun updateLikedPostsADD(
        postId:String,
        auth: FirebaseAuth = FirebaseAuth.getInstance(),
        firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    ) {
        firestore.collection("Users").document(auth.currentUser!!.uid).update("likes", FieldValue.arrayUnion(postId))
            .addOnCompleteListener {
                if(it.isSuccessful){
                    //Toast.makeText(context,"item Added to the list ",Toast.LENGTH_LONG).show()
                }else{
                    //Toast.makeText(context,"likes doesn't change",Toast.LENGTH_LONG).show()

                }
            }
    }
    fun updateLikedPostsREMOVE(
        postId:String,
        auth: FirebaseAuth = FirebaseAuth.getInstance(),
        firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    ) {
        firestore.collection("Users").document(auth.currentUser!!.uid).update("likes", FieldValue.arrayRemove(postId))
            .addOnCompleteListener {
                if(it.isSuccessful){
                   // Toast.makeText(context,"item removed from the list",Toast.LENGTH_SHORT).show()
                }else{
                    //Toast.makeText(context,"likes doesn't change",Toast.LENGTH_SHORT).show()

                }
            }
    }

    fun updateLikesCount(
        docId:String,
        LikesCount:Int,
        firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    ) {
        firestore.collection("Posts").document(docId).update("likesCount",LikesCount)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    //Toast.makeText(context,"the list of ur liked posts changed",Toast.LENGTH_LONG).show()
                }else{
                    //Toast.makeText(context,"the list still the same",Toast.LENGTH_LONG).show()

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
