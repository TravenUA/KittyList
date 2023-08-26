package com.traven.kittylist.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traven.kittylist.domain.Irepo
import com.traven.kittylist.domain.Repository
import com.traven.kittylist.model.KittyDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: Irepo
) : ViewModel() {

    private val TAG = "MyViewModel"
    private var _currentPage = 0
    private val _pageSize: Int = 20
    private var _scrollPosition: Int = 0


    private var runtimeLoading = false
    var errorState = mutableStateOf(false)
    val dataList = mutableStateListOf<KittyDTO>()

    init {
        getData()
    }

    fun updateScrollPosition(position: Int) {
        _scrollPosition = position
        //Log.d(TAG, "Scroll position is ${_scrollPosition}")
        checkLoadDataNecessary()
    }

    private fun checkLoadDataNecessary() {
        val scrollCondition = (_scrollPosition + 4) >= dataList.size

        if (scrollCondition && !runtimeLoading) {
            _currentPage++
            getData()
            //Log.d(TAG, "Loading started at position ${_scrollPosition}")
        }
    }

    private fun getData() {
        runtimeLoading = true
        errorState.value = false
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseList =
                    repository.getList(_pageSize, _currentPage)
                        .filter { !it.url.endsWith(".gif") }
                        .filter { it.width > 200 }
                dataList.addAll(responseList)
            } catch (e: Exception) {
                //Log.d(TAG, "Request failed with ${e.message}")
                errorState.value = true
            } finally {
                runtimeLoading = false
            }
        }
    }

}