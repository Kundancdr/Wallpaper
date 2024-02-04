package com.example.wallpaperapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class WallpaperRVAdapter (

    private val wallpaperlist: List<String>, private val context: Context
    ):  RecyclerView.Adapter<WallpaperRVAdapter.WallpaperItemViewHolder>() {

    class WallpaperItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wallpaperIV: ImageView = itemView.findViewById(R.id.wallpaper)
        val wallpaperCV: CardView = itemView.findViewById(R.id.cv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperItemViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_rv_item,parent,false)
        return WallpaperItemViewHolder(itemview)
    }

    override fun getItemCount(): Int {
      return wallpaperlist.size
    }

    override fun onBindViewHolder(holder: WallpaperItemViewHolder, position: Int) {
       val wallpaperitem = wallpaperlist[position]
        Picasso.get().load(wallpaperitem).into(holder.wallpaperIV)
        holder.wallpaperCV.setOnClickListener{
            val intent = Intent(holder.itemView.context,wallpaperActivity::class.java)
            intent.putExtra("imgUrl",wallpaperitem)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}