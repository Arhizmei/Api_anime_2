package com.zmei.api_anime

import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {
    private var itemCount = 0
    val imageList = mutableListOf<Image_Anime>()


    fun loadData(
        waifuApiService: WaifuApiService,
        callback: (Boolean) -> Unit
    ) {
        waifuApiService.getWaifuImage().enqueue(object : Callback<ImageModel?> {
            override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                if (response.isSuccessful) {
                    val image = response.body()
                    if (image != null) {
                        itemCount++
                        val imageAnime = Image_Anime(image, "Image $itemCount")
                        imageList.add(imageAnime)
                        callback(true)
                    } else {
                        callback(false)
                    }
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: retrofit2.Call<ImageModel?>, t: Throwable) {
                callback(false)
            }
        })
    }
}
