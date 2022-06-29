package com.example.letstalk.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letstalk.R
import com.example.letstalk.databinding.FragmentContactsBinding
import com.example.letstalk.entity.User
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

    private lateinit var _adapter: FirebaseRecyclerAdapter<User, ContactsHolder>
    private lateinit var refContacts: DatabaseReference
    private lateinit var refUsers: DatabaseReference

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
        initRecycler()
    }

    private fun initRecycler() {
        refContacts = REF_DATABASE_ROOT.child(NODE_EMAILS_CONTACTS)
            .child(UID)

        val options = FirebaseRecyclerOptions.Builder<User>()
            .setQuery(refContacts, User::class.java)
            .build()

        _adapter = object: FirebaseRecyclerAdapter<User, ContactsHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
                return ContactsHolder(view)
            }

            override fun onBindViewHolder(holder: ContactsHolder, position: Int, model: User) {
                refUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)
                refUsers.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val contact = snapshot.getUserModel()
                        holder.name.text = contact.name + " " + contact.lastname
                        holder.status.text = contact.status
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }
        }

        binding.recyclerListContacts.adapter = _adapter
        _adapter.startListening()
    }

    private fun initListeners() {

        binding.textSearchFriends.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val value = p0.toString()
                searchUser(value)
            }
        })
    }

    class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.contact_fullname)
        val status: TextView = view.findViewById(R.id.status)
    }
}