package com.pinto.storyappkt.ui.main.list_story

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinto.storyappkt.R
import com.pinto.storyappkt.databinding.FragmentListStoryBinding
import com.pinto.storyappkt.utils.ViewModelFactory


class ListStoryFragment : Fragment() {

    private var _binding: FragmentListStoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: StoriesAdapter
    private val listStoryViewModel: ListStoryViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =  FragmentListStoryBinding.inflate(inflater, container, false)
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        listStoryViewModel.stories.observe(viewLifecycleOwner){
            data ->
            if (data != null){
                adapter.submitData(viewLifecycleOwner.lifecycle, data)
            }
        }

        setupAdapter()
        binding.fabCreateStory.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.createStoryFragment)
        )
        onBackPressed()



    }

    private fun setupAdapter() {
        val layoutInflater = LinearLayoutManager(context)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL
        binding.rvStories.layoutManager = layoutInflater

        adapter = StoriesAdapter { story, imageView, nameView, descView ->
            val action = ListStoryFragmentDirections.actionListStoryFragmentToDetailStoryFragment(
                id = story.id,
                name = story.name,
                description = story.description,
                photoUrl = story.photoUrl
            )

            findNavController().navigate(action,
                FragmentNavigator.Extras.Builder()
                    .addSharedElements(
                        mapOf(
                            imageView to imageView.transitionName,
                            nameView to nameView.transitionName,
                            descView to descView.transitionName,
                        )
                    ).build()
            )
        }
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = StoriesLoadingAdapter{
                adapter.retry()
            }
        )
        binding.rvStories.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}