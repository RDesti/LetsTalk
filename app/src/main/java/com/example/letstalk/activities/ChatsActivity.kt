package com.example.letstalk.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.letstalk.R
import com.example.letstalk.databinding.ActivityChatsBinding
import com.example.letstalk.entity.User
import com.example.letstalk.enum.EAppStates
import com.example.letstalk.fragments.ChatFragment
import com.example.letstalk.utilits.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityChatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initListeners()
        initUser()
    }

    override fun onStart() {
        super.onStart()
        EAppStates.updateState(EAppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        EAppStates.updateState(EAppStates.OFFLINE)
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    USER = snapshot.getValue(User::class.java) ?: User()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun initListeners() {
        _binding.mainToolbar.setNavigationOnClickListener {
            this.supportFragmentManager.popBackStack()
        }
        _binding.mainToolbar.setOnMenuItemClickListener { item ->
            when(item?.itemId) {
                R.id.settings_menu_exit -> {
                    AUTH.signOut()
                    goToLogin()
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        changeToolbar()
    }

    private fun goToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun changeToolbar() {
        if (supportFragmentManager.fragments == ChatFragment::class.java) {
            this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }
}