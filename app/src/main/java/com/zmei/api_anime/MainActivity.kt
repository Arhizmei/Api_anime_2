package com.zmei.api_anime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zmei.api_anime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = AnimeAdapter()
    private val imageIdList = listOf(R.drawable.anime1, R.drawable.anime2, R.drawable.anime3)
    private var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 3)
            rcView.adapter = adapter
            buttonAdd.setOnClickListener {
                if (index>2) index = 0
                val imageAnime = Image_Anime(imageIdList[index], "Image $index")
                adapter.addImage(imageAnime)
                index++
            }
        }
    }
}