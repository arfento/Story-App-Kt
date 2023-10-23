package com.pinto.storyappkt.ui.auth.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.pinto.storyappkt.R
import com.pinto.storyappkt.data.models.login.LoginResponse
import com.pinto.storyappkt.data.remote.Result

import com.pinto.storyappkt.databinding.FragmentLoginBinding
import com.pinto.storyappkt.utils.Preference
import com.pinto.storyappkt.utils.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLoginDontHaveAccount.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.registerFragment)
        )

        binding.btLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            val inputService =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputService.hideSoftInputFromWindow(it.windowToken, 0)

            loginViewModel.login(email, password).observe(requireActivity()) { result ->
              if(result != null) {
                  print("login result : ${result.toString()}")
                  when(result){
                      is Result.Loading ->{
                          showLoading(true)
                      }
                      is Result.Success ->{
                          processLogin(result.data)
                          showLoading(false)
                      }
                      is Result.Error ->{
                          showLoading(false)
                          Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                      }
                  }
              }
            }
        }

        val isFormRegister : Boolean? = arguments?.getBoolean("is_from_register")
        if (isFormRegister != null && isFormRegister){
            onbackPressed()
        }

    }

    private fun onbackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object :OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }

    private fun processLogin(data: LoginResponse) {
        if(data.error){
            Toast.makeText(requireContext(), data.message, Toast.LENGTH_LONG).show()
        }else{
            Preference.saveToken(data.loginResult.token, requireContext())
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            requireActivity().finish()
        }
    }

    private fun showLoading(state: Boolean) {
        binding.pbLogin.isVisible = state
        binding.edLoginEmail.isInvisible = state
        binding.edLoginPassword.isInvisible = state
        binding.btLogin.isInvisible = state
        binding.textView6.isInvisible = state
        binding.tvLoginDontHaveAccount.isInvisible = state
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}