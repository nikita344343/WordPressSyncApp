package com.wordpresssync.app.data

import com.wordpresssync.app.api.WordPressApi
import com.wordpresssync.app.api.model.WpPost
import com.wordpresssync.app.api.model.WpUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WordPressRepository(private val settings: SettingsRepository) {

    private var api: WordPressApi? = null

    private fun getOrCreateApi(): WordPressApi? {
        val baseUrl = settings.getApiBaseUrl() ?: return null
        if (api == null) {
            val client = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                .build()
            api = Retrofit.Builder()
                .baseUrl("$baseUrl/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WordPressApi::class.java)
        }
        return api
    }

    suspend fun getPosts(perPage: Int = 20, page: Int = 1): Result<List<WpPost>> = withContext(Dispatchers.IO) {
        val api = getOrCreateApi()
            ?: return@withContext Result.failure(Exception("URL сайта не задан. Укажи его в настройках."))
        runCatching {
            val response = api.getPosts(perPage = perPage, page = page)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Ошибка ${response.code()}: ${response.message()}")
            }
        }
    }

    suspend fun getUsers(perPage: Int = 50, page: Int = 1): Result<List<WpUser>> = withContext(Dispatchers.IO) {
        val api = getOrCreateApi()
            ?: return@withContext Result.failure(Exception("URL сайта не задан."))
        runCatching {
            val response = api.getUsers(perPage = perPage, page = page)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Ошибка ${response.code()}")
            }
        }
    }

    fun clearApiCache() {
        api = null
    }
}
