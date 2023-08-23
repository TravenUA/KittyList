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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val TAG = "MyViewModel"
    private var _currentPage = 0
    private val _pageSize: Int = 20
    private var _scrollPosition: Int = 0

    val loadingState = mutableStateOf(true)
    var error = false
    val dataList = mutableStateListOf<KittyDTO>()


/*    private val _mainData = MutableLiveData<List<KittyDTO>>()
    val outData: LiveData<List<KittyDTO>> = _mainData*/

    init {
        getData()
    }

    fun hasData() = dataList.isNotEmpty()

    fun updateScrollPosition(position: Int) {
        _scrollPosition = position
        checkLoadDataNecessary()
    }

    private fun checkLoadDataNecessary() {
        val scrollCondition = (_scrollPosition + 3) >= _pageSize * _currentPage

        if (scrollCondition && !loadingState.value) {
            _currentPage++
            getData()
        }
    }

    private fun getData() {
        loadingState.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getList(_pageSize, _currentPage)
                dataList.addAll(response)
                error = false
            } catch (e: Exception) {
                Log.d(TAG, "Request failed with ${e.message}")
                error = true
            } finally {
                withContext(Dispatchers.Main) {
                    loadingState.value = false
                }
            }
        }
    }
}