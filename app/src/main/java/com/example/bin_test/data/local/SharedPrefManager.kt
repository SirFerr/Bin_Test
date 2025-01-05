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

    // Сохранение данных
    fun saveBinResponse(binResponse: BinResponse) {
        val historyList = getBinHistory().toMutableList()
        historyList.add(binResponse)
        val json = gson.toJson(historyList)
        sharedPreferences.edit().putString("bin_history", json).apply()
    }

    // Получение данных
    fun getBinHistory(): List<BinResponse> {
        val json = sharedPreferences.getString("bin_history", null)
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<BinResponse>>() {}.type
            gson.fromJson(json, type)
        }
    }

    // Очистка истории
    fun clearHistory() {
        sharedPreferences.edit().remove("bin_history").apply()
    }
}
