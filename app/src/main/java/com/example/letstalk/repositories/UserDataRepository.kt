package com.example.letstalk.repositories

import com.example.letstalk.entity.UserRegisterData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor() : IUserDataRepository{
    override var userRegisterData: UserRegisterData = UserRegisterData()

    override fun clearData() {
        userRegisterData.email = null
        userRegisterData.name = null
        userRegisterData.secondName = null
        userRegisterData.password = null
    }
}