package com.example.letstalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.letstalk.databinding.FragmentChatListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListFragment : Fragment() {
    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatListBinding.inflate(layoutInflater)
        return binding.root
    }
}