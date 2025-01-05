package com.example.bin_test.data.local

import android.content.Context
import com.example.bin_test.data.model.BinResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManager @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences("bin_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Сохранение BIN
    fun saveBin(bin: String) {
        val binList = getBinList().toMutableList()
        binList.add(bin)
        val json = gson.toJson(binList)
        sharedPreferences.edit().putString("bin_list", json).apply()
    }

    // Получение списка BIN
    fun getBinList(): List<String> {
        val json = sharedPreferences.getString("bin_list", null)
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        }
    }
    // Сохранение BinResponse
    fun saveBinResponse(bin: String, response: BinResponse) {
        val binMap = getBinMap().toMutableMap()
        binMap[bin] = response
        val json = gson.toJson(binMap)
        sharedPreferences.edit().putString("bin_map", json).apply()
    }

    // Получение BinResponse
    fun getBinMap(): Map<String, BinResponse> {
        val json = sharedPreferences.getString("bin_map", null)
        return if (json.isNullOrEmpty()) {
            emptyMap()
        } else {
            val type = object : TypeToken<Map<String, BinResponse>>() {}.type
            gson.fromJson(json, type)
        }
    }

}

