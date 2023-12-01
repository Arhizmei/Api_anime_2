package com.zmei.api_anime


import retrofit2.Call
import retrofit2.http.GET

interface WaifuApiService {
    @GET("sfw/waifu")
    suspend fun getWaifuImage(): ImageModel
}