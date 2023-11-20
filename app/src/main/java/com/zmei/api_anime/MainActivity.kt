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
    lateinit var adapter: AnimeAdapter
    private lateinit var viewModel: MyViewModel
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcView.layoutManager = GridLayoutManager(this, 3)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        adapter = AnimeAdapter { viewModel.loadData() }
        binding.rcView.adapter = adapter
//        binding.rcView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                loadMoreData()
//            }
//        })
//        loadMoreData()
    }

//    private fun loadMoreData() {
//        if (isLoading) {
//            return
//        }
//        isLoading = true
//
//        repeat(20) {
//            viewModel.loadData()
//        }
//
//    }
}
