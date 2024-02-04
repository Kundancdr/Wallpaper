package com.example.wallpaperapp

import android.graphics.pdf.PdfDocument.Page

data class WallpaperRVModel(

    var page: Int,
    var per_page: Int,
    var photos: ArrayList<photos>,
    var  total_results: Int,
    var next_page:String

)
