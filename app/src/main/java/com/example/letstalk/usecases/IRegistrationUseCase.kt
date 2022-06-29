package com.example.letstalk.usecases

import com.example.letstalk.entity.UserRegisterData

interface IRegistrationUseCase {
    fun getUserData(): UserRegisterData
    fun clearUserData ()
}