package com.example.bin_test.feature.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bin_test.data.Repository
import com.example.bin_test.data.model.BinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _history = MutableStateFlow<List<Pair<String, BinResponse>>>(emptyList())
    val history = _history.asStateFlow()

    fun getHistory() {
        val binMap = repository.getBinResponses()
        _history.value = binMap.toList()
    }
}
