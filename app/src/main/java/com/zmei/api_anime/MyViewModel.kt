package com.zmei.api_anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val waifuApiService: WaifuApiService
) : ViewModel() {
    private var itemCount = 0
    private val _imageList = MutableLiveData<List<Image_Anime>>()
    val imageList: LiveData<List<Image_Anime>> get() = _imageList
    suspend fun loadData() {
        val imageResult = withContext(Dispatchers.IO) {
            kotlin.runCatching { waifuApiService.getWaifuImage() }
        }
        imageResult.onSuccess { image ->
            itemCount++
            val imageAnime = Image_Anime(image, "Image $itemCount")
            _imageList.value = (_imageList.value ?: emptyList()) + listOf(imageAnime)
        }
    }
}
