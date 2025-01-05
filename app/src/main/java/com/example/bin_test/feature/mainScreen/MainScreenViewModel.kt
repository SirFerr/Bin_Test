package com.example.bin_test.feature.mainScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
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
class MainScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _textValue = MutableStateFlow<String>("45717360")
    val textValue = _textValue.asStateFlow()

    private val _cardInfo = MutableStateFlow<BinResponse?>(null)
    val cardInfo = _cardInfo.asStateFlow()

    fun updateText(text: String) {
        _textValue.value = text
    }

    fun getInfo() {
        Log.d("GetInfo", "getInfo: getinfo")
        viewModelScope.launch {
            try {
                Log.d("GetInfo", textValue.value)
                val response = repository.getBinInfo(textValue.value)
                Log.d("GetInfo", response.body().toString())
                println(response.isSuccessful)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _cardInfo.value = it
                        // Сохранение BIN и данных карты
                        repository.saveBinResponse(textValue.value, it)
                    }
                }
                repository.saveBin(textValue.value)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    // Открыть URL в браузере
    fun openBrowser(context: Context, url: String) {
        if (url != null && url != "") {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            context.startActivity(intent)
        }
    }

    // Открыть телефон в звонилке
    fun openDialer(context: Context, phone: String) {
        if (phone != null && phone != "") {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            context.startActivity(intent)
        }
    }

    // Открыть координаты в картах
    fun openMaps(context: Context, latitude: Int, longitude: Int) {
        if (latitude != null && longitude != null) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:$latitude,$longitude")
            }
            context.startActivity(intent)
        }
    }
}