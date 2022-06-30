package com.example.letstalk.utilits

import com.example.letstalk.entity.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: UserModel

const val TYPE_TEXT = "text"
const val TYPE_IMAGE = "image"

const val NODE_USERS = "users"
const val NODE_EMAILS = "emails"
const val NODE_EMAILS_CONTACTS = "emails_contacts"
const val NODE_MESSAGES = "messages"

const val CHILD_ID = "id"
const val CHILD_EMAIL = "email"
const val CHILD_USER_NAME = "username"
const val CHILD_USER_LASTNAME = "userlastname"
const val CHILD_STATUS = "status"
const val CHILD_TEXT = "text"
const val CHILD_TYPE = "type"
const val CHILD_FROM = "from"
const val CHILD_TIMESTAMP = "timestamp"


fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    USER = UserModel()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

fun searchUser(email: String) {
    if (AUTH.currentUser != null)
        REF_DATABASE_ROOT.child(NODE_EMAILS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        if (it.key == email.replace('.', '_')) {
                            REF_DATABASE_ROOT.child(NODE_EMAILS_CONTACTS)
                                .child(UID)
                                .child(it.value.toString())
                                .child(CHILD_ID)
                                .setValue(it.value.toString())
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

fun DataSnapshot.getUserModel(): UserModel =
    this.getValue(UserModel::class.java) ?: UserModel()

fun sendMessage(message: String, receivingUserId: String, typeText: String, function: () -> Unit) {
    val refDialogUser = "$NODE_MESSAGES/$UID/$receivingUserId"
    val refDialogReceivingUser = "$NODE_MESSAGES/$receivingUserId/$UID"
    val messageKey = REF_DATABASE_ROOT.child(refDialogUser).push().key

    val mapMessage = hashMapOf<String, Any>()
    mapMessage[CHILD_FROM] = UID
    mapMessage[CHILD_ID] = messageKey.toString()
    mapMessage[CHILD_TYPE] = typeText
    mapMessage[CHILD_TEXT] = message
    mapMessage[CHILD_TIMESTAMP] = ServerValue.TIMESTAMP

    val mapDialog = hashMapOf<String, Any>()
    mapDialog["$refDialogUser/$messageKey"] = mapMessage
    mapDialog["$refDialogReceivingUser/$messageKey"] = mapMessage

    REF_DATABASE_ROOT.updateChildren(mapDialog)
        .addOnSuccessListener { function() }
        .addOnFailureListener {
            //todo error message
        }
}