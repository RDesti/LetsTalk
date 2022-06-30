package com.example.letstalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.R
import com.example.letstalk.databinding.FragmentContactsBinding
import com.example.letstalk.entity.UserModel
import com.example.letstalk.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private var _adapter: FirebaseRecyclerAdapter<UserModel, ContactsHolder>? = null
    private lateinit var refContacts: DatabaseReference
    private lateinit var refUsers: DatabaseReference
    private lateinit var refUsersListener: ValueEventListener
    private var mapListeners = HashMap<DatabaseReference, ValueEventListener>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        initRecycler()
    }

    private fun initRecycler() {
        refContacts = REF_DATABASE_ROOT.child(NODE_EMAILS_CONTACTS)
            .child(UID)

        val options = FirebaseRecyclerOptions.Builder<UserModel>()
            .setQuery(refContacts, UserModel::class.java)
            .build()

        _adapter = object : FirebaseRecyclerAdapter<UserModel, ContactsHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_item, parent, false)
                return ContactsHolder(view)
            }

            override fun onBindViewHolder(holder: ContactsHolder, position: Int, model: UserModel) {
                refUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)

                refUsersListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val contact = snapshot.getUserModel()
                        holder.name.text = contact.username + " " + contact.userlastname
                        holder.status.text = contact.status
                        holder.itemView.setOnClickListener {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .addToBackStack(null)
                                .replace(
                                    R.id.fragmentContainerView,
                                    SingleChatFragment(contact)
                                ).commit()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }

                refUsers.addValueEventListener(refUsersListener)
                mapListeners[refUsers] = refUsersListener
            }
        }

        binding.recyclerListContacts.adapter = _adapter
        _adapter?.startListening()
    }

    private fun initListeners() {
        binding.buttonAddFriend.setOnClickListener {
            hideKeyboard(requireActivity())
            val value = binding.textSearchFriends.text.toString()
            searchUser(value)
        }
    }

    class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.contact_fullname)
        val status: TextView = view.findViewById(R.id.status)
    }

    override fun onPause() {
        super.onPause()
        _adapter?.stopListening()
        mapListeners.forEach {
            it.key.removeEventListener(it.value)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
}