package com.pinto.storyappkt.di

import android.content.Context
import com.pinto.storyappkt.data.remote.StoryRepository
import com.pinto.storyappkt.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService(context)
        return StoryRepository(apiService)
    }
}