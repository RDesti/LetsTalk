package com.example.letstalk.utilits

import com.example.letstalk.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: User

const val NODE_USERS = "users"
const val NODE_EMAILS = "emails"
const val NODE_EMAILS_CONTACTS = "emails_contacts"

const val CHILD_ID = "id"
const val CHILD_EMAIL = "email"
const val CHILD_USER_NAME = "username"
const val CHILD_USER_LASTNAME = "userlastname"
const val CHILD_STATE = "state"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    USER = User()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

fun searchUser(email: String) {
    REF_DATABASE_ROOT.child(NODE_EMAILS)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    if (snapshot.key == email) {
                        REF_DATABASE_ROOT.child(NODE_EMAILS_CONTACTS)
                            .child(UID)
                            .child(snapshot.value.toString())
                            .child(CHILD_ID)
                            .setValue(snapshot.value.toString())
                            .addOnFailureListener {
                                //todo error
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
}

fun DataSnapshot.getUserModel(): User =
    this.getValue(User::class.java) ?: User()