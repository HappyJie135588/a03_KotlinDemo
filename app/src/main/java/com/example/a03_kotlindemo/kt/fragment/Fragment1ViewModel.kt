package com.example.a03_kotlindemo.kt.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a03_kotlindemo.kt.Register
import com.example.a03_kotlindemo.kt.repository.Repository
import kotlinx.coroutines.launch

class Fragment1ViewModel : ViewModel() {
    var register: MutableLiveData<Register> = MutableLiveData()
    private var repository = Repository()
    fun toRegister(name: String) {
        viewModelScope.launch {
            register.value = repository.toRegister(name)
        }
    }
}