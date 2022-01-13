package com.shahad.instic.utils

import android.content.Context
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig

fun initializeAlbumLib(context: Context) {
	Album.initialize(
		AlbumConfig.newBuilder(context)
			.setAlbumLoader(MediaLoader())
			.build()
	)
}