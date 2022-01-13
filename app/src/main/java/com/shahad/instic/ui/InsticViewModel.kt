package com.shahad.instic.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Completable

class InsticViewModel : ViewModel() {
	
	private val firebaseAuth = FirebaseAuth.getInstance()
	
	fun checkConnection(context: Context): Boolean {
		val connectivityManager =
			context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val network = connectivityManager.activeNetwork ?: return false
		val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
		return when {
			activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
			else -> false
		}
	}
	
	fun login(email: String, password: String) = Completable.create { emitter ->
		firebaseAuth
			.signInWithEmailAndPassword(email, password)
			.addOnSuccessListener {
				emitter.onComplete()
			}
			.addOnFailureListener { exception ->
				emitter.onError(exception)
			}
	}
	
	fun register(email: String, displayName: String, password: String) =
		Completable.create { emitter ->
			if (listOf(email, displayName, password).any { it.isEmpty() }) {
				emitter.onError(Throwable("All fields are required"))
			}
			
			firebaseAuth
				.createUserWithEmailAndPassword(email, password)
				.addOnSuccessListener {
					updateUserProfile(displayName)
					emitter.onComplete()
				}
				.addOnFailureListener { exception ->
					emitter.onError(exception)
				}
		}
	
	fun updateUserProfile(
		displayname: String = getCurrentUser()!!.displayName ?: "",
		photoUri: Uri? = null
	) {
		getCurrentUser()?.updateProfile(
			UserProfileChangeRequest.Builder()
				.setDisplayName(displayname)
				.setPhotoUri(photoUri)
				.build()
		)
	}
	
	fun getCurrentUser() = firebaseAuth.currentUser
	
	fun signOut() = firebaseAuth.signOut()
}