package com.example.letstalk.utilits

import com.example.letstalk.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: User

const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_EMAIL = "email"
const val CHILD_USER_NAME = "username"
const val CHILD_USER_LASTNAME = "userlastname"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    USER = User()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}