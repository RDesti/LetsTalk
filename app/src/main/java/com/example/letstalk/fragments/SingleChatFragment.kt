package com.example.letstalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letstalk.R
import com.example.letstalk.adapters.SingleChatAdapter
import com.example.letstalk.databinding.FragmentSingleChatBinding
import com.example.letstalk.entity.User
import com.example.letstalk.utilits.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class SingleChatFragment(contact: User) : Fragment() {
    private var _binding: FragmentSingleChatBinding? = null
    private val binding get() = _binding!!

    private val _contact = contact

    private lateinit var listenerInfoToolbar: ValueEventListener
    private lateinit var receivingUser: User
    private lateinit var toolbarInfo: View
    private lateinit var infoContactFullname: TextView
    private lateinit var infoContactStatus: TextView
    private lateinit var refUser: DatabaseReference
    private lateinit var refMessages: DatabaseReference
    private lateinit var adapter: SingleChatAdapter
    private lateinit var messagesListener: ValueEventListener
    private var listMessages = emptyList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initToolbar()
        initRecyclerView()

        binding.imageSend.setOnClickListener {
            val message = binding.chatInputMessage.text.toString()
            if (message.isEmpty()) showToast("Enter your message")
            else sendMessage(message, _contact.id, TYPE_TEXT) {
                binding.chatInputMessage.setText("")
            }
        }
    }

    private fun initRecyclerView() {
        adapter = SingleChatAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd = true
        binding.chatRecyclerView.layoutManager = linearLayoutManager
        refMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES)
            .child(UID).child(_contact.id)
        binding.chatRecyclerView.adapter = adapter
        messagesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMessages = snapshot.children.map { it.getUserModel() }
                adapter.setList(listMessages)
                binding.chatRecyclerView.smoothScrollToPosition(adapter.itemCount)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        refMessages.addValueEventListener(messagesListener)
    }

    private fun initToolbar() {
        toolbarInfo = AppConstants.CHATS_ACTIVITY.findViewById(R.id.toolbar_info)
        infoContactFullname = AppConstants.CHATS_ACTIVITY.findViewById(R.id.contact_fullname)
        infoContactStatus = AppConstants.CHATS_ACTIVITY.findViewById(R.id.status)
        toolbarInfo.visibility = View.VISIBLE
        listenerInfoToolbar = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                receivingUser = snapshot.getUserModel()
                infoContactFullname.text = _contact.username + " " + _contact.userlastname
                infoContactStatus.text = _contact.status
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        refUser = REF_DATABASE_ROOT.child(NODE_USERS).child(_contact.id)
        refUser.addValueEventListener(listenerInfoToolbar)
    }

    override fun onPause() {
        super.onPause()
        toolbarInfo.visibility = View.GONE
        refUser.removeEventListener(listenerInfoToolbar)
        refMessages.removeEventListener(messagesListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}