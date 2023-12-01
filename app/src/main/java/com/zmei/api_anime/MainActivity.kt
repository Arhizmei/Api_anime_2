package com.zmei.api_anime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import com.zmei.api_anime.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: AnimeAdapter
    val viewModel by viewModels<MyViewModel>()
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcView.layoutManager = GridLayoutManager(this, 3)
        //viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        val loadMoreListener = { loadMoreListener() }
        adapter = AnimeAdapter(loadMoreListener)
        binding.rcView.adapter = adapter
        viewModel.imageList.observe(this, { updatedImageList ->
            adapter.addImage(updatedImageList)
            isLoading = false
        })
        loadMoreListener()
    }

    private fun loadMoreListener() {
        if (!isLoading) {
            isLoading = true
            lifecycleScope.launch {
                viewModel.loadData()
            }
        }
    }
}