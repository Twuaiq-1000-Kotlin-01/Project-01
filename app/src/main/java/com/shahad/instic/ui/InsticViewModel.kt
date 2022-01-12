package com.shahad.instic.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Completable

class InsticViewModel : ViewModel() {
	
	private val firebaseAuth = FirebaseAuth.getInstance()
	
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
	
	private fun updateUserProfile(displayname: String) {
		getCurrentUser()?.updateProfile(
			UserProfileChangeRequest.Builder()
				.setDisplayName(displayname)
				.build()
		)
	}
	
	fun getCurrentUser() = firebaseAuth.currentUser
	
	fun signOut() = firebaseAuth.signOut()
}