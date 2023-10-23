package com.pinto.storyappkt.ui.main.create_story

import androidx.lifecycle.ViewModel
import com.pinto.storyappkt.data.remote.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun postStory(file: MultipartBody.Part, description: RequestBody) = storyRepository.postStory(file, description)
}