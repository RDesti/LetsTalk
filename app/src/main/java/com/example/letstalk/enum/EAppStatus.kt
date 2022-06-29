package com.example.letstalk.enum

import com.example.letstalk.utilits.*

enum class EAppStatus(val status: String) {
    ONLINE("online"),
    OFFLINE("last seen recently"),
    TYPING("typing...");

    companion object {
        fun updateState(appStates: EAppStatus) {
            if (AUTH.currentUser != null)
                REF_DATABASE_ROOT.child(NODE_USERS)
                    .child(UID).child(CHILD_STATUS)
                    .setValue(appStates.status)
                    .addOnSuccessListener {
                        USER.status = appStates.status
                    }
                    .addOnFailureListener {
                        //todo error message
                    }
        }
    }
}