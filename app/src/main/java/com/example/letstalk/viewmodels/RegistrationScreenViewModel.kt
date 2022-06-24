package com.example.letstalk.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letstalk.entity.RequestResultData
import com.example.letstalk.enum.EResultLoginType
import com.example.letstalk.enum.EValidationType
import com.example.letstalk.usecases.LoginUseCase
import com.example.letstalk.usecases.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val regUseCase: RegistrationUseCase
) : ViewModel() {
    var password: String = ""

    private val _isValidName = MutableLiveData<Boolean>().apply { value = true }
    val isValidName: LiveData<Boolean>
        get() = _isValidName

    private val _isValidLastName = MutableLiveData<Boolean>().apply { value = true }
    val isValidLastName: LiveData<Boolean>
        get() = _isValidLastName

    private val _isValidPassword = MutableLiveData<Boolean>().apply { value = true }
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _isValidConfirmPassword = MutableLiveData<Boolean>().apply { value = true }
    val isValidConfirmPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _isValidEmail = MutableLiveData<Boolean>().apply { value = true }
    val isValidEmail: LiveData<Boolean>
        get() = _isValidEmail

    private val _isRegistrationSuccess = MutableLiveData<RequestResultData>()
    val isRegistrationSuccess: LiveData<RequestResultData>
        get() = _isRegistrationSuccess

    private val _isLoginSuccess = MutableLiveData<RequestResultData>()
    val isLoginSuccess: LiveData<RequestResultData>
        get() = _isLoginSuccess

    fun registration(name: String, lastName: String, email: String, password: String) {
        val userData = regUseCase.getUserData()
        userData.name = name
        userData.secondName = lastName
        userData.email = email
        userData.password = password
        userData.confirmPassword = password

        viewModelScope.launch {
            regUseCase.register(userData)
                .catch {
                    _isRegistrationSuccess.value = RequestResultData(EResultLoginType.ERROR)
                }
                .collect {
                    withContext(Dispatchers.Main) {
                        _isRegistrationSuccess.value = it

//                        if (it.resultType == EResultLoginType.SUCCESS) {
//                            _isRegistrationSuccess.value = EResultType.SUCCESS
//                        } else {
//                            _isRegistrationSuccess.value = EResultType.ERROR
//                        }
                    }
                }
        }
    }

    fun login() {
        if (regUseCase.getUserData().email.isNullOrEmpty() || regUseCase.getUserData().password.isNullOrEmpty()) return
        viewModelScope.launch {
            loginUseCase.execute(
                regUseCase.getUserData().email!!,
                regUseCase.getUserData().password!!
            )
                .collect {
                    withContext(Dispatchers.Main) {
                        if (it.resultType == EResultLoginType.SUCCESS) {
                            regUseCase.clearUserData()
                        }
                        _isLoginSuccess.value = it
                    }
                }
        }
    }

    fun validate(type: EValidationType, value: String?) {
        when (type) {
            EValidationType.NAME -> {
                _isValidName.value = value.isNullOrEmpty()
            }
            EValidationType.LAST_NAME -> {
                _isValidLastName.value = value.isNullOrEmpty()
            }
            EValidationType.EMAIL -> {
                _isValidEmail.value = value.isNullOrEmpty()
            }
            EValidationType.PASSWORD -> {
                _isValidPassword.value = value.isNullOrEmpty()
            }
            EValidationType.CONFIRM_PASSWORD -> {
                _isValidConfirmPassword.value = value.isNullOrEmpty() && value == password
            }
            else->{}
        }
    }
}