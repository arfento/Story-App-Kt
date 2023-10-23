package com.pinto.storyappkt.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pinto.storyappkt.R
import com.pinto.storyappkt.databinding.FragmentSplashBinding
import com.pinto.storyappkt.utils.Preference

class SplashFragment : Fragment() {

    companion object {
        private const val DURATION: Long = 1500
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = Preference.initPref(requireContext(), "onSignIn")
        val token = sharedPref.getString("token", "")

        var action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()

        if (token != "") {
            action = SplashFragmentDirections.actionSplashFragmentToMainActivity()
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(action)
                requireActivity().finish()
            }, DURATION)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(action)
            }, DURATION)
        }
    }


}