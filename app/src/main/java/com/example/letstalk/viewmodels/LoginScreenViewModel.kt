package com.example.letstalk.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letstalk.entity.RequestResultData
import com.example.letstalk.enum.EResultLoginType
import com.example.letstalk.enum.EValidationType
import com.example.letstalk.usecases.ILoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: ILoginUseCase
) : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var emailError = MutableLiveData<String>()
    var passwordError = MutableLiveData<String>()

    private val _isValidEmail = MutableLiveData<Boolean>().apply { value = true }
    val isValidEmail: LiveData<Boolean>
        get() = _isValidEmail

    private val _isValidPassword = MutableLiveData<Boolean>().apply { value = true }
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _isLoginSuccess = MutableLiveData<RequestResultData>()
    val isLoginSuccess: LiveData<RequestResultData>
        get() = _isLoginSuccess

    fun login() {
        _isValidEmail.value = !email.value.isNullOrEmpty()
        _isValidPassword.value = !password.value.isNullOrEmpty()
        if (email.value.isNullOrEmpty() || password.value.isNullOrEmpty())
            return
        _isLoginSuccess.value = RequestResultData(EResultLoginType.LOADING)
        viewModelScope.launch {
            loginUseCase.execute(email.value.toString(), password.value.toString())
                .collect {
                    withContext(Dispatchers.Main) {
                        _isLoginSuccess.value = it
                    }
                }
        }
    }

    fun validate(type: EValidationType, value: String?) {
        when (type) {
            EValidationType.EMAIL -> {
                emailError.value = if (value.isNullOrEmpty()) "Requires field" else null
                _isValidEmail.value = !value.isNullOrEmpty()
            }
            EValidationType.PASSWORD -> {
                passwordError.value = if (value.isNullOrEmpty()) "Requires field" else null
                _isValidPassword.value = !value.isNullOrEmpty()
            }
            else -> {}
        }
    }
}