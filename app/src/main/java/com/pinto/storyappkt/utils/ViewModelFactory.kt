package com.pinto.storyappkt.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pinto.storyappkt.di.Injection
import com.pinto.storyappkt.ui.auth.login.LoginViewModel
import com.pinto.storyappkt.ui.auth.register.RegisterViewModel
import com.pinto.storyappkt.ui.main.create_story.CreateStoryViewModel
import com.pinto.storyappkt.ui.main.list_story.ListStoryViewModel
import com.pinto.storyappkt.ui.map.MapsViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ListStoryViewModel::class.java) ->{
                ListStoryViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) ->{
                MapsViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->{
                RegisterViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(CreateStoryViewModel::class.java) ->{
                CreateStoryViewModel(Injection.provideRepository(context)) as T
            }

            else -> throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}