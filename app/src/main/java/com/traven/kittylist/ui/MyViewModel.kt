package com.traven.kittylist.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traven.kittylist.domain.Repository
import com.traven.kittylist.model.dto.KittyDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val TAG = "MyViewModel"
    private var _currentPage = 0
    private val _pageSize: Int = 20
    private var _scrollPosition: Int = 0


    var runtimeLoading = false
    var errorState = mutableStateOf(false)
    val dataList = mutableStateListOf<KittyDTO>()

    init {
        getData()
    }

    fun updateScrollPosition(position: Int) {
        _scrollPosition = position
        Log.d(TAG, "Scroll position is ${_scrollPosition}")
        checkLoadDataNecessary()
    }

    private fun checkLoadDataNecessary() {
        val scrollCondition = (_scrollPosition + 2) >= dataList.size

        if (scrollCondition && !runtimeLoading) {
            _currentPage++
            getData()
            Log.d(TAG, "Loading started at position ${_scrollPosition}")
        }
    }

    private fun getData() {
        runtimeLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getList(_pageSize, _currentPage)
                dataList.addAll(response)
                errorState.value = false
            } catch (e: Exception) {
                Log.d(TAG, "Request failed with ${e.message}")
                errorState.value = true
            } finally {
                runtimeLoading = false
            }
        }
    }
}