package com.example.wallpaperapp

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

class wallpaperActivity : AppCompatActivity() {

    lateinit var wallpaperIV: ImageView
    lateinit var setWallpaperBtn: Button
    lateinit var imgUrl:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        wallpaperIV = findViewById(R.id.wallpaperid)
        setWallpaperBtn = findViewById(R.id.buttonSetWallpaper)
        imgUrl = intent.getStringExtra("imgUrl").toString()
        Picasso.get().load(imgUrl).into(wallpaperIV)


        setWallpaperBtn.setOnClickListener{
             val bitmap = (wallpaperIV.drawable as BitmapDrawable).bitmap
             val wallpaperManager = WallpaperManager.getInstance(baseContext)
             wallpaperManager.setBitmap(bitmap)
            Toast.makeText(this,"Wallpaper set !",Toast.LENGTH_SHORT).show()

        }
    }
}