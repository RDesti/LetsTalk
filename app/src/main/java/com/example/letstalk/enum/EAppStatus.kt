package com.example.letstalk.enum

import com.example.letstalk.utilits.CHILD_STATE
import com.example.letstalk.utilits.NODE_USERS
import com.example.letstalk.utilits.REF_DATABASE_ROOT
import com.example.letstalk.utilits.USER

enum class EAppStatus(val state: String) {
    ONLINE("online"),
    OFFLINE("last seen recently"),
    TYPING("typing...");

    companion object {
        fun updateState(appStates: EAppStatus) {
            REF_DATABASE_ROOT.child(NODE_USERS).child(CHILD_STATE)
                .setValue(appStates.state)
                .addOnSuccessListener {
                    USER.status = appStates.state
                }
                .addOnFailureListener{
                    //todo error message
                }
        }
    }
}