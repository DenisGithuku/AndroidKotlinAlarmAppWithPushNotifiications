package com.denisgithuku.notifications.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denisgithuku.notifications.databinding.FragmentExtrasBinding

class ExtrasFragment : Fragment() {
    private lateinit var binding: FragmentExtrasBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExtrasBinding.inflate(layoutInflater)
        return binding.root
    }
}