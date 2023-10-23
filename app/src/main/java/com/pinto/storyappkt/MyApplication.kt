package com.pinto.storyappkt

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory

class MyApplication : Application(), ImageLoaderFactory{
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }
}