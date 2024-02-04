package com.example.wallpaperapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitAPI {


    @Headers("Authorization: NWD3CiMKkljg5RZp3Kbs5qAoJ0dkAq2NYX8oYQ8b5sgsxEhdAovxu7nd")
    @GET("curated?per_page=30&page=1")
    fun getWallpapers():Call<WallpaperRVModel>


    @Headers("Authorization: NWD3CiMKkljg5RZp3Kbs5qAoJ0dkAq2NYX8oYQ8b5sgsxEhdAovxu7nd")
    @GET("search?")
    fun getWallpaperByCatogery(@Query("query") category: String,
                               @Query("per_page") per_page: Int,
                               @Query("page") page: Int,
                               ): Call<WallpaperRVModel>
}