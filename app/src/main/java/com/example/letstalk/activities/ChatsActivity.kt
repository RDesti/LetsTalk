package com.example.letstalk.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.letstalk.R
import com.example.letstalk.databinding.ActivityChatsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityChatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initListeners()
    }

    private fun initListeners() {
        _binding.mainToolbar.setOnMenuItemClickListener { item ->
            when(item?.itemId) {
                R.id.settings_menu_exit -> {} //todo exit to login screen
            }
            true
        }
    }

}