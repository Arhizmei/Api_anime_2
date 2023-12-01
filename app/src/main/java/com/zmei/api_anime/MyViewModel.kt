package com.zmei.api_anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(): ViewModel() {
    private var itemCount = 0
    private val _imageList = MutableLiveData<List<Image_Anime>>()
    val imageList: LiveData<List<Image_Anime>> get() = _imageList
    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()
    val retrofitClient = RetrofitClient(okHttpClient)
    val waifuApiService = retrofitClient.retrofit.create(WaifuApiService::class.java)
    suspend fun loadData() {
        try {
            val response = withContext(Dispatchers.IO) {
                waifuApiService.getWaifuImage().execute()
            }

            if (response.isSuccessful) {
                val image = response.body()
                if (image != null) {
                    itemCount++
                    val imageAnime = Image_Anime(image, "Image $itemCount")
                    _imageList.value = (_imageList.value ?: emptyList()) + listOf(imageAnime)
                }
            } else {
                // Обработка ошибок
            }
        } catch (e: Exception) {
            // Обработка исключений
        }
    }
}
