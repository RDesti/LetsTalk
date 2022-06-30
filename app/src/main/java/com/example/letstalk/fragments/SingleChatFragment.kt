package com.example.letstalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.R
import com.example.letstalk.adapters.SingleChatAdapter
import com.example.letstalk.databinding.FragmentSingleChatBinding
import com.example.letstalk.entity.UserModel
import com.example.letstalk.utilits.*
import com.google.firebase.database.*

class SingleChatFragment(contact: UserModel) : Fragment() {
    private var _binding: FragmentSingleChatBinding? = null
    private val binding get() = _binding!!

    private val _contact = contact

    private lateinit var listenerInfoToolbar: ValueEventListener
    private lateinit var receivingUserModel: UserModel
    private lateinit var toolbarInfo: View
    private lateinit var infoContactFullname: TextView
    private lateinit var infoContactStatus: TextView
    private lateinit var refUser: DatabaseReference
    private lateinit var refMessages: DatabaseReference
    private lateinit var adapter: SingleChatAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var messagesListener: ChildEventListener

    private var countMessages = 10
    private var isScrolling = false
    private var isSmoothToPosition = true

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
            isSmoothToPosition = true
            val message = binding.chatInputMessage.text.toString()
            if (message.isEmpty()) showToast("Enter your message")
            else sendMessage(message, _contact.id, TYPE_TEXT) {
                binding.chatInputMessage.setText("")
            }
        }
    }

    private fun initRecyclerView() {
        adapter = SingleChatAdapter()
        linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd = true
        binding.chatRecyclerView.layoutManager = linearLayoutManager
        refMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES)
            .child(UID).child(_contact.id)
        binding.chatRecyclerView.adapter = adapter
        binding.chatRecyclerView.setHasFixedSize(true)
        binding.chatRecyclerView.isNestedScrollingEnabled = false
        messagesListener = object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getUserModel()

                if (isSmoothToPosition) {
                    adapter.addItemToBottom(message) {
                        binding.chatRecyclerView.smoothScrollToPosition(adapter.itemCount)
                    }
                } else {
                    adapter.addItemToTop(message) {
                        binding.chatSwipeRefreshLayout.isRefreshing = false
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        }
        refMessages.limitToLast(countMessages).addChildEventListener(messagesListener)

        binding.chatRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolling && dy < 0 && linearLayoutManager.findFirstVisibleItemPosition() <= 3) {
                    updateData()
                }
            }
        })

        binding.chatSwipeRefreshLayout.setOnRefreshListener { updateData() }
    }

    private fun updateData() {
        isSmoothToPosition = false
        isScrolling = false
        countMessages += 10
        refMessages.removeEventListener(messagesListener)
        refMessages.limitToLast(countMessages).addChildEventListener(messagesListener)
    }

    private fun initToolbar() {
        toolbarInfo = AppConstants.CHATS_ACTIVITY.findViewById(R.id.toolbar_info)
        infoContactFullname = AppConstants.CHATS_ACTIVITY.findViewById(R.id.contact_fullname)
        infoContactStatus = AppConstants.CHATS_ACTIVITY.findViewById(R.id.status)
        toolbarInfo.visibility = View.VISIBLE
        listenerInfoToolbar = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                receivingUserModel = snapshot.getUserModel()
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