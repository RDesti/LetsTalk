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
import com.example.letstalk.databinding.FragmentRegistrationBinding
import com.example.letstalk.enum.EResultLoginType
import com.example.letstalk.enum.EValidationType
import com.example.letstalk.viewmodels.RegistrationScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val _viewModel by lazy { ViewModelProvider(this)[RegistrationScreenViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObserves()
        validateText()
    }

    private fun initObserves() {
        _viewModel.isRegistrationSuccess.observe(viewLifecycleOwner) {
            when (it.resultType) {
                EResultLoginType.ERROR -> {}//showError(it.msg)
                EResultLoginType.SUCCESS -> _viewModel.login()
                else -> {}
            }
        }

        _viewModel.isLoginSuccess.observe(viewLifecycleOwner) {
            when (it.resultType) {
                EResultLoginType.SUCCESS -> goToMainChatScreen()
                EResultLoginType.ERROR -> {}//showError(it.msg)
                else -> {}
            }
        }
    }

    private fun initListeners() {
        binding.logInTextView.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.registrationButton.setOnClickListener {
            registration()
        }
    }

    private fun registration() {
        _viewModel.validate(EValidationType.NAME, binding.nameEditText.text.toString())
        _viewModel.validate(EValidationType.LAST_NAME, binding.lastNameEditText.text.toString())
        _viewModel.validate(EValidationType.EMAIL, binding.emailEditText.text.toString())
        _viewModel.validate(EValidationType.PASSWORD, binding.passwordEditText.text.toString())
        _viewModel.validate(EValidationType.CONFIRM_PASSWORD, binding.confirmPasswordEditText.text.toString())

        if (_viewModel.isValidName.value == true
            && _viewModel.isValidLastName.value == true
            && _viewModel.isValidEmail.value == true
            && _viewModel.isValidPassword.value == true
            && _viewModel.isValidConfirmPassword.value == true) {
            _viewModel.registration(
                binding.nameEditText.text.toString(),
                binding.lastNameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                binding.confirmPasswordEditText.text.toString()
            )
        } else {
            setValidationColor(
                _viewModel.nameError.value,
                binding.nameErrorTextView,
                binding.nameEditText
            )
            setValidationColor(
                _viewModel.lastNameError.value,
                binding.lastNameErrorTextView,
                binding.lastNameEditText
            )
            setValidationColor(
                _viewModel.emailError.value,
                binding.emailError,
                binding.emailEditText
            )
            setValidationColor(
                _viewModel.passwordError.value,
                binding.passwordError,
                binding.passwordEditText
            )
            setValidationColor(
                _viewModel.confirmPasswordError.value,
                binding.confirmPasswordError,
                binding.confirmPasswordEditText
            )
        }
    }

    private fun goToMainChatScreen() {
        val intent = Intent(requireContext(), ChatsActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun validateText() {
        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val value = p0.toString()
                _viewModel.validate(EValidationType.NAME, value)
                setValidationColor(
                    _viewModel.nameError.value,
                    binding.nameErrorTextView,
                    binding.nameEditText
                )
            }
        })

        binding.lastNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val value = p0.toString()
                _viewModel.validate(EValidationType.LAST_NAME, value)
                setValidationColor(
                    _viewModel.lastNameError.value,
                    binding.lastNameErrorTextView,
                    binding.lastNameEditText
                )
            }
        })

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
                    binding.emailError,
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
                    binding.passwordError,
                    binding.passwordEditText
                )
            }
        })

        binding.confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val value = p0.toString()
                _viewModel.validate(EValidationType.CONFIRM_PASSWORD, value)
                setValidationColor(
                    _viewModel.confirmPasswordError.value,
                    binding.confirmPasswordError,
                    binding.confirmPasswordEditText
                )
            }
        })
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}