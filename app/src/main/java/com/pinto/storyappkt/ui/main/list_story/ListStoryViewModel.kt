package com.pinto.storyappkt.ui.main.list_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pinto.storyappkt.data.models.stories.Story
import com.pinto.storyappkt.data.remote.StoryRepository

class ListStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    val stories : LiveData<PagingData<Story>> = storyRepository.getStories().cachedIn(viewModelScope)
}