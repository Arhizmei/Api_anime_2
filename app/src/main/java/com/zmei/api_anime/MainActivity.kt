package com.zmei.api_anime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import androidx.recyclerview.widget.GridLayoutManager
import com.zmei.api_anime.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import okhttp3.Callback
import okhttp3.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AnimeAdapter
    private val imageIdList = listOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcView.layoutManager = GridLayoutManager(this, 3)
        adapter = AnimeAdapter()
        binding.rcView.adapter = adapter
        binding.buttonAdd.setOnClickListener {
            var index = 0
            val waifuApiService = RetrofitClient.retrofit.create(WaifuApiService::class.java)
            waifuApiService.getWaifuImage().enqueue(object : Callback<ImageModel> {
                override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                    if (response.isSuccessful) {
                        val image = response.body()
                        if (image != null) {
                            val imageAnime = Image_Anime(imageIdList[index], "Image $index")
                            adapter.addImage(imageAnime)
                            index++
                        }
                    }
                }

                override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                    // Обработайте ошибку загрузки изображения
                }
            })
        }
    }


}
