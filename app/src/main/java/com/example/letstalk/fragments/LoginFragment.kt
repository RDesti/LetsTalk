package com.example.letstalk.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.letstalk.R
import com.example.letstalk.activities.ChatsActivity
import com.example.letstalk.databinding.FragmentLoginBinding
import com.example.letstalk.enum.EResultLoginType
import com.example.letstalk.enum.EValidationType
import com.example.letstalk.utilits.hideKeyboard
import com.example.letstalk.viewmodels.LoginScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val _viewModel by lazy { ViewModelProvider(this)[LoginScreenViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container,false)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validate()
        initListeners()
        initObserves()
    }

    private fun initListeners() {
        binding.registerTextView.setOnClickListener {
            this.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        binding.loginButton.setOnClickListener {
            _viewModel.validate(EValidationType.EMAIL, binding.emailEditText.text.toString())
            _viewModel.validate(EValidationType.PASSWORD, binding.passwordEditText.text.toString())
            setValidationColor(
                _viewModel.emailError.value,
                binding.emailErrorTextView,
                binding.emailEditText
            )
            setValidationColor(
                _viewModel.passwordError.value,
                binding.passwordErrorTextView,
                binding.passwordEditText
            )
            if (_viewModel.isValidEmail.value == true && _viewModel.isValidPassword.value == true) {
                hideKeyboard(requireActivity())
                _viewModel.login()
            }
        }
    }

    private fun initObserves() {
        _viewModel.isLoginSuccess.observe(viewLifecycleOwner) {
            when (it.resultType) {
                EResultLoginType.LOADING -> {}//showSpinner()
                EResultLoginType.SUCCESS -> goToMainChatScreen()
                EResultLoginType.ERROR -> {}//showError(it.msg)
            }
        }
    }

    private fun goToMainChatScreen() {
        val intent = Intent(requireContext(), ChatsActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setValidationColor(value: String?, text: TextView, editText: EditText) {
        if (value == null) {
            text.text = ""
            editText.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.teal_700))
        } else {
            text.text = value
            editText.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.red_color))
        }
    }

    private fun validate() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val value = p0.toString()
                _viewModel.validate(EValidationType.EMAIL, value)
                setValidationColor(
                    _viewModel.emailError.value,
                    binding.emailErrorTextView,
                    binding.emailEditText
                )
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val value = p0.toString()
                _viewModel.validate(EValidationType.PASSWORD, value)
                setValidationColor(
                    _viewModel.passwordError.value,
                    binding.passwordErrorTextView,
                    binding.passwordEditText
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}