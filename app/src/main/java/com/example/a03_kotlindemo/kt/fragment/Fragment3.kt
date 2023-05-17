package com.example.a03_kotlindemo.kt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a03_kotlindemo.databinding.Fragment3Binding
import com.example.a03_kotlindemo.kt.KotlinMainActivity

class Fragment3 : Fragment() {
    private lateinit var binding: Fragment3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment3Binding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.btnMainFragment2.setOnClickListener {
            requireActivity().let {
                it as KotlinMainActivity
                it.addFragment(Fragment2())
            }
        }
    }
}