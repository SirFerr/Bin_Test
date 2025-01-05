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
    private val _textValue = MutableStateFlow<String>("")
    val textValue = _textValue.asStateFlow()

    private val _cardInfo = MutableStateFlow<BinResponse?>(null)
    val cardInfo = _cardInfo.asStateFlow()

    fun updateText(text: String){
        _textValue.value=text
    }

    fun getInfo() {
        viewModelScope.launch {
            try {
                val response = repository.getBinInfo(textValue.value)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _cardInfo.value = it
                        repository.saveBinResponse(binResponse = it)
                    }
                }
            } catch (e: Exception) {
            }

        }
    }
}