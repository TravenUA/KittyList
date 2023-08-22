package com.traven.kittylist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traven.kittylist.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _inData = MutableLiveData<String>()
    val outData: LiveData<String> = _inData

    init {
        this.viewModelScope.launch(Dispatchers.IO) {
            repository.getList(10)
        }
    }
}