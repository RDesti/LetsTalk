package com.example.letstalk.usecases

import com.example.letstalk.entity.UserData
import com.example.letstalk.repositories.IUserDataRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val userDataRepository: IUserDataRepository
    ) : IRegistrationUseCase {

    override fun getUserData(): UserData {
        return userDataRepository.userData
    }

    override fun clearUserData () {
        userDataRepository.clearData()
    }
}