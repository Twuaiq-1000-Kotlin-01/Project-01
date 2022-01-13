package com.shahad.instic.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shahad.instic.databinding.FragmentPostBinding
import com.shahad.instic.ui.MainViewModel
import com.shahad.instic.ui.MediaLoader
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private var imagePath: String? = null
    private val disposables = CompositeDisposable()
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)

        initializeAlbumLib()

        binding.attachMediaButton.setOnClickListener {
            Album.album(this)
                .singleChoice()
                .camera(true)
                .filterMimeType {
                    it.equals("jpeg", true) || it.equals("png", true)
                }
                .afterFilterVisibility(true)
                .onResult {
                    val file = it.first()
                    imagePath = file.path
                    binding.attachMediaButton.text = "Attach (1)"
                }
                .onCancel {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
                .start()
        }

        binding.postButton.setOnClickListener {
            val content = binding.postContentEditText.text.toString()
            onPostInitiated()

            viewModel.newPost(content, imagePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onPostCreated()
                    Toast.makeText(activity, "Post created successfully!", Toast.LENGTH_SHORT)
                        .show()
                }, { error ->
                    Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                }).also { disposable ->
                    disposables.add(disposable)
                }
        }

        return binding.root
    }

    private fun initializeAlbumLib() {
        Album.initialize(
            AlbumConfig.newBuilder(requireContext())
                .setAlbumLoader(MediaLoader())
                .build()
        )
    }

    private fun onPostCreated() = with(binding) {
        postButton.isEnabled = true
        attachMediaButton.apply {
            isEnabled = true
            text = "Attach"
        }
        progressBar.visibility = View.GONE
        postContentEditText.apply {
            isEnabled = true
            text.clear()
        }
        imagePath = null
    }

    private fun onPostInitiated() = with(binding) {
        postButton.isEnabled = false
        attachMediaButton.isEnabled = false
        postContentEditText.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostFragment()
    }
}