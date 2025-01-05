package com.example.bin_test.feature.mainScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bin_test.data.Repository
import com.example.bin_test.data.model.BinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
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

    // Открыть URL в браузере
    fun openBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        context.startActivity(intent)
    }

    // Открыть телефон в звонилке
    fun openDialer(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        context.startActivity(intent)
    }

    // Открыть координаты в картах
    fun openMaps(context: Context, latitude: Int, longitude: Int) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:$latitude,$longitude")
        }
        context.startActivity(intent)
    }
}