package com.zmei.api_anime

import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    val imageList: MutableList<Image_Anime> = mutableListOf<Image_Anime>()
}