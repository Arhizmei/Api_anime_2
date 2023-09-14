package com.zmei.api_anime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.zmei.api_anime.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AnimeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcView.layoutManager = GridLayoutManager(this, 3)
        adapter = AnimeAdapter()
        binding.rcView.adapter = adapter
        var index = 0
        binding.buttonAdd.setOnClickListener {
            val waifuApiService = RetrofitClient.retrofit.create(WaifuApiService::class.java)
            waifuApiService.getWaifuImage().enqueue(object : Callback<ImageModel?> {
                override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                    if (response.isSuccessful) {
                        val image = response.body()
                        if (image != null) {
                            val imageAnime = Image_Anime(image, "Image $index")
                            adapter.addImage(imageAnime)
                            index++
                        }
                    }
                }

                override fun onFailure(call: retrofit2.Call<ImageModel?>, t: Throwable) {
                    var index = 0
                    val imageUrl = "https://i.waifu.pics/Tj6Wzwo.png"
                    val image = ImageModel(imageUrl)
                    val imageAnime = Image_Anime(image, "Image $index")
                    adapter.addImage(imageAnime)
                    index++
                }
            })
        }
    }


}
