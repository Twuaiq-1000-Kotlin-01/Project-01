package com.shahad.instic.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shahad.instic.databinding.FragmentSignupBinding
import com.shahad.instic.ui.InsticViewModel
import com.shahad.instic.ui.home.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignupFragment : Fragment() {
	
	private lateinit var binding: FragmentSignupBinding
	private val viewModel by activityViewModels<InsticViewModel>()
	private val disposables = CompositeDisposable()
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentSignupBinding.inflate(inflater, container, false)
		
		with(binding) {
			registerButton.setOnClickListener {
				val disposable = viewModel.register(
					emailAddressEditText.text.toString(),
					displaynameEditText.text.toString(),
					passwordEditText.text.toString()
				)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe({
						Intent(activity, MainActivity::class.java).also { startActivity(it) }
						requireActivity().finish()
					}, {
						Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
						it.printStackTrace()
					})
				
				disposables.add(disposable)
			}
			
			signupTextView.setOnClickListener {
				(activity as AuthActivity).navigateTo(LoginFragment.newInstance())
			}
		}
		
		return binding.root
	}
	
	override fun onStop() {
		super.onStop()
		disposables.clear()
	}
	
	companion object {
		@JvmStatic
		fun newInstance() = SignupFragment()
		
	}
}