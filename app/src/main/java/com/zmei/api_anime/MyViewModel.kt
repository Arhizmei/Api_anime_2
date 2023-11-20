package com.zmei.api_anime

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel(private val act: MainActivity) : ViewModel() {
    val imageList = mutableListOf<Image_Anime>()
    private val okHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    private val retrofitClient = RetrofitClient(okHttpClient)
    private val waifuApiService = retrofitClient.retrofit.create(WaifuApiService::class.java)
    var loadedItemCount = 0

    fun loadData() {
        waifuApiService.getWaifuImage().enqueue(object : Callback<ImageModel?> {
            override fun onResponse(
                call: Call<ImageModel?>,
                response: Response<ImageModel?>
            ) {
                if (response.isSuccessful) {
                    val image = response.body()
                    if (image != null) {
                        val imageAnime = Image_Anime(image, "Image ${loadedItemCount + 1}")
                        imageList.add(imageAnime)
                        act.adapter.addImage(imageList)
                        loadedItemCount++
                        Log.d("mylog", "$loadedItemCount")
                        act.isLoading = false
                    }
                }
                act.isLoading = false
            }

            override fun onFailure(call: retrofit2.Call<ImageModel?>, t: Throwable) {
                Toast.makeText(act, "Error", Toast.LENGTH_LONG).show()
                act.isLoading = false

            }
        })
    }
}