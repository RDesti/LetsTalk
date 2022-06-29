package com.example.letstalk.repositories

import com.example.letstalk.entity.UserRegisterData

interface IUserDataRepository {
    var userRegisterData: UserRegisterData
    fun clearData()
}