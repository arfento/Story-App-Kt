package com.pinto.storyappkt.ui.auth.register

import androidx.lifecycle.ViewModel
import com.pinto.storyappkt.data.remote.StoryRepository

class RegisterViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = storyRepository.postRegister(name, email, password)

}