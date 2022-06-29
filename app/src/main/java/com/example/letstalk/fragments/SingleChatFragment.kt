package com.example.letstalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.letstalk.R
import com.example.letstalk.entity.User
import com.example.letstalk.utilits.AppConstants
import com.example.letstalk.utilits.NODE_USERS
import com.example.letstalk.utilits.REF_DATABASE_ROOT
import com.example.letstalk.utilits.getUserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class SingleChatFragment(contact: User) : Fragment() {

    private val _contact = contact

    private lateinit var listenerInfoToolbar: ValueEventListener
    private lateinit var receivingUser: User
    private lateinit var toolbarInfo: View
    private lateinit var infoContactFullname: TextView
    private lateinit var infoContactStatus: TextView
    private lateinit var refUser: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onResume() {
        super.onResume()
        toolbarInfo = AppConstants.CHATS_ACTIVITY.findViewById(R.id.toolbar_info)
        infoContactFullname = AppConstants.CHATS_ACTIVITY.findViewById(R.id.contact_fullname)
        infoContactStatus = AppConstants.CHATS_ACTIVITY.findViewById(R.id.status)
        toolbarInfo.visibility = View.VISIBLE
        listenerInfoToolbar = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                receivingUser = snapshot.getUserModel()
                initToolbar()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        refUser = REF_DATABASE_ROOT.child(NODE_USERS).child(_contact.id)
        refUser.addValueEventListener(listenerInfoToolbar)
    }

    private fun initToolbar() {
        infoContactFullname.text = _contact.username + " " + _contact.userlastname
        infoContactStatus.text = _contact.status
    }

    override fun onPause() {
        super.onPause()
        toolbarInfo.visibility = View.GONE
        refUser.removeEventListener(listenerInfoToolbar)
    }
}