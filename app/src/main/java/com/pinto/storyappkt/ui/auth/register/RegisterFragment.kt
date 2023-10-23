package com.pinto.storyappkt.ui.auth.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.pinto.storyappkt.R
import com.pinto.storyappkt.data.models.register.RegisterResponse
import com.pinto.storyappkt.data.remote.Result
import com.pinto.storyappkt.databinding.FragmentRegisterBinding
import com.pinto.storyappkt.utils.ViewModelFactory


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSignupHaveAccount.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.loginFragment))

        binding.btSignUp.setOnClickListener { it->
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            val inputService =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputService.hideSoftInputFromWindow(it.windowToken, 0)

            registerViewModel.register(name ,email, password).observe(requireActivity()) { it ->
                if(it != null) {
                    when(it){
                        is Result.Loading ->{
                            showLoading(true)
                        }
                        is Result.Success ->{
                            showLoading(false)
                            processRegister(it.data)
                        }
                        is Result.Error ->{
                            showLoading(false)
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun processRegister(data: RegisterResponse) {
        if(data.error){
            Toast.makeText(requireContext(), "Gagal register new user", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireContext(),"Register new user berhasil, Silahkan login!", Toast.LENGTH_LONG).show()
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(isFromRegister = true))
        }
    }

    private fun showLoading(state: Boolean) {
        binding.pbCreateRegister.isVisible = state
        binding.edRegisterEmail.isInvisible = state
        binding.edRegisterName.isInvisible = state
        binding.edRegisterPassword.isInvisible = state
        binding.textView2.isInvisible = state
        binding.tvSignupHaveAccount.isInvisible = state
        binding.btSignUp.isInvisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}