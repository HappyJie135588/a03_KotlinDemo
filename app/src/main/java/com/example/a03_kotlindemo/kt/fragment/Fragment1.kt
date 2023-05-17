package com.example.a03_kotlindemo.kt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a03_kotlindemo.databinding.Fragment1Binding

class Fragment1 : Fragment() {
    private lateinit var binding: Fragment1Binding
    private lateinit var model: Fragment1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment1Binding.inflate(inflater, container, false)
        lifecycle.addObserver(Fragment1LifecycleObserver())
        model = ViewModelProvider(this).get(Fragment1ViewModel::class.java)
        initView()
        return binding.root
    }

    private fun initView() {
        model.register.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.tvViewModel.text = it.msg
        })
        binding.btnViewModel.setOnClickListener {
            model.toRegister("HappyJie135588")
        }
    }
}