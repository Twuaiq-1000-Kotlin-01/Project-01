package com.tuwaiq.projectone.timeLineFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.projectone.model.Post
import com.tuwaiq.projectone.model.User
import com.tuwaiq.projectone.repo.Repo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TimeLineViewModel : ViewModel() {
    private val repo: Repo = Repo.getInstance()


    suspend fun getAllPosts(): Flow<MutableList<Post>> {
        return repo.getPosts()
    }

    suspend fun getUserInfo(): Flow<User> {

        return repo.getUserInfo()
    }
    fun getLikedPosts():Flow<String>{
        return flow{
            repo.getLikesPosts()
        }
    }

    fun getLikesCount(docId:String):Flow<Int>{
        return flow{
            repo.getLikesCount(docId)
        }
    }
    fun updateLikesCount(docId:String,newLikes:Int){
        viewModelScope.launch{
            repo.updateLikesCount(docId,newLikes)
        }

    }

    fun updateLikedPostADD(post:String){
        viewModelScope.launch{
            repo.updateLikedPostsADD(post)
        }

    }


    fun updateLikedPostREMOVE(post:String){
        viewModelScope.launch{
            repo.updateLikedPostsREMOVE(post)
        }

    }

}