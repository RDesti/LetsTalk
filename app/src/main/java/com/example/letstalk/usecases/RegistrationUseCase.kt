package com.example.letstalk.usecases

import com.example.letstalk.entity.UserRegisterData
import com.example.letstalk.repositories.IUserDataRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val userDataRepository: IUserDataRepository
) : IRegistrationUseCase {

    override fun getUserData(): UserRegisterData {
        return userDataRepository.userRegisterData
    }

    override fun clearUserData() {
        userDataRepository.clearData()
    }
}