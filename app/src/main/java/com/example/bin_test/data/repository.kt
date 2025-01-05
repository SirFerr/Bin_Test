package com.example.bin_test.data

import com.example.bin_test.data.local.SharedPrefManager
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
    fun saveBin(bin: String) {
        sharedPrefManager.saveBin(bin)
    }

    // Получение всей истории
    fun getHistory(): List<String> {
        return sharedPrefManager.getBinList()
    }

    // Сохранение BinResponse в репозиторий
    fun saveBinResponse(bin: String, response: BinResponse) {
        sharedPrefManager.saveBinResponse(bin, response)
    }

    // Получение списка BIN с данными карты
    fun getBinResponses(): Map<String, BinResponse> {
        return sharedPrefManager.getBinMap()
    }


}
