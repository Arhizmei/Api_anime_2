package com.zmei.api_anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {
    private var itemCount = 0
    private val _imageList = MutableLiveData<List<Image_Anime>>()
    val imageList: LiveData<List<Image_Anime>> get() = _imageList
    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()
    val retrofitClient = RetrofitClient(okHttpClient)
    val waifuApiService = retrofitClient.retrofit.create(WaifuApiService::class.java)
    fun loadData( ) {
        waifuApiService.getWaifuImage().enqueue(object : Callback<ImageModel?> {
            override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                if (response.isSuccessful) {
                    val image = response.body()
                    if (image != null) {
                        itemCount++
                        val imageAnime = Image_Anime(image, "Image $itemCount")
                        _imageList.value = (_imageList.value ?: emptyList()) + listOf(imageAnime)
                    }
                }
            }

            override fun onFailure(call: Call<ImageModel?>, t: Throwable) {
                // Обработка ошибок
            }
        })
    }
}
