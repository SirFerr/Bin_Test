package com.example.bin_test.data

import SharedPrefManager
import com.example.bin_test.data.model.BinResponse
import com.example.bin_test.data.remote.BinListApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val sharedPrefManager: SharedPrefManager,
    private val binListApi: BinListApi,
) {

    // Получение информации по BIN с API
    suspend fun getBinInfo(bin: String): Response<BinResponse> {
        return withContext(Dispatchers.IO) {
            binListApi.getInfo(bin)
        }
    }

    // Сохранение запроса в истории
    fun saveBinResponse(binResponse: BinResponse) {
        sharedPrefManager.saveBinResponse(binResponse)
    }

    // Получение всей истории
    fun getHistory(): List<BinResponse> {
        return sharedPrefManager.getBinHistory()
    }

    // Очистка истории
    fun clearHistory() {
        sharedPrefManager.clearHistory()
    }
}
