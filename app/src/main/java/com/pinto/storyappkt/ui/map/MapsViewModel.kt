package com.pinto.storyappkt.ui.map

import androidx.lifecycle.ViewModel
import com.pinto.storyappkt.data.remote.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStoriesWithLocation() = storyRepository.getStoriesWithLocation()
}