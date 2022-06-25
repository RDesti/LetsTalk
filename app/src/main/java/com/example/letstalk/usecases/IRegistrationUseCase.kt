package com.example.letstalk.usecases

import com.example.letstalk.entity.UserData

interface IRegistrationUseCase {
    fun getUserData(): UserData
    fun clearUserData ()
}