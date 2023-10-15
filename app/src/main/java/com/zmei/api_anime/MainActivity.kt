package com.zmei.api_anime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zmei.api_anime.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AnimeAdapter
    private lateinit var viewModel: MyViewModel
    var loadedItemCount = 0
    var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val retrofitClient = RetrofitClient(okHttpClient)
        binding.rcView.layoutManager = GridLayoutManager(this, 3)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        adapter = AnimeAdapter(viewModel) { loadMoreData(retrofitClient) }
        binding.rcView.adapter = adapter
        binding.rcView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                loadMoreData(retrofitClient)
            }
        })
        loadMoreData(retrofitClient)
    }
    private fun loadMoreData(retrofitClient: RetrofitClient) {
        if (isLoading) {
            return
        }
        isLoading = true
        binding.button.setOnClickListener {
            repeat(1002){
        val waifuApiService = retrofitClient.retrofit.create(WaifuApiService::class.java)
        waifuApiService.getWaifuImage().enqueue(object : Callback<ImageModel?> {
            override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                if (response.isSuccessful) {
                    val image = response.body()
                    if (image != null) {
                        val imageAnime = Image_Anime(image, "Image ${loadedItemCount + 1}")
                        adapter.addImage(imageAnime)
                        loadedItemCount++
                        Log.d("mylog", "${loadedItemCount}")
                        isLoading = false
                    }
                }
                isLoading = false
            }

            override fun onFailure(call: retrofit2.Call<ImageModel?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                isLoading = false

            }
        })
    }
    }}
}
