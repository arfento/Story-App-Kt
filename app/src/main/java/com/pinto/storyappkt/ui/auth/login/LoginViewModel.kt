package com.pinto.storyappkt.ui.auth.login

import androidx.lifecycle.ViewModel
import com.pinto.storyappkt.data.remote.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun login(name : String, password : String) = storyRepository.postLogin(name,password)

}