package com.example.a03_kotlindemo.kt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a03_kotlindemo.kt.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var register: MutableLiveData<Register> = MutableLiveData()
    private var repository = Repository()
    fun toRegister(name: String) {
        viewModelScope.launch {
            register.value = repository.toRegister(name)
        }
    }
}