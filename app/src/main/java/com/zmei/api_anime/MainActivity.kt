package com.zmei.api_anime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zmei.api_anime.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: AnimeAdapter
    private lateinit var viewModel: MyViewModel
    var isLoading = false
    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()
    val retrofitClient = RetrofitClient(okHttpClient)
    val waifuApiService = retrofitClient.retrofit.create(WaifuApiService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcView.layoutManager = GridLayoutManager(this, 3)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        adapter = AnimeAdapter { loadMoreListener() }
        binding.rcView.adapter = adapter
        loadMoreListener()
    }

    private fun loadMoreListener() {
        if (!isLoading) {
            isLoading = true
            viewModel.loadData(waifuApiService) { success ->
                if (success) {
                    adapter.addImage(viewModel.imageList)
                } else {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                }
                isLoading = false
            }
        }
    }
}