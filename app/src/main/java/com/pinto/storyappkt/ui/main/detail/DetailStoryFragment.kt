package com.pinto.storyappkt.ui.main.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionInflater
import coil.imageLoader
import coil.request.ImageRequest
import com.pinto.storyappkt.R
import com.pinto.storyappkt.data.models.stories.Story
import com.pinto.storyappkt.databinding.FragmentDetailStoryBinding

class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailStoryBinding.inflate(inflater, container, false)
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        binding.story = Story(
            createdAt = "",
            description = arguments?.getString("description") ?: "",
            id = arguments?.getString("id") ?: "",
            lat = arguments?.getDouble("lat") ?: 0.0,
            lon = arguments?.getDouble("lon") ?: 0.0,
            name = arguments?.getString("name") ?: "",
            photoUrl = arguments?.getString("photo_url") ?: "",
        )

        val request = ImageRequest.Builder(requireContext())
            .data(arguments?.getString("photo_url"))
            .target(
                onSuccess = {
                    startPostponedEnterTransition()
                },
                onError = {
                    startPostponedEnterTransition()
                }
            ).build()

        requireActivity().application.imageLoader.enqueue(request)

        binding.executePendingBindings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}