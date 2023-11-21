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

class MyViewModel : ViewModel() {
    val imageList: MutableList<Image_Anime> = mutableListOf<Image_Anime>()

    fun loadMoreData(apiService: WaifuApiService, loadedItemCount: Int, callback: (Boolean) -> Unit) {
        apiService.getWaifuImage().enqueue(object : Callback<ImageModel?> {
            override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                if (response.isSuccessful) {
                    val image = response.body()
                    if (image != null) {
                        val imageAnime = Image_Anime(image, "Image ${loadedItemCount + 1}")
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
