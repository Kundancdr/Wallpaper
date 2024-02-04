package com.example.wallpaperapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CategoryRVAdapter(
    private val categoryList: List<CatogeryRVModel>,
    private val ctx: Context,
    private val categoryClickInterface: CatogeryClickInterface
):
    RecyclerView.Adapter<CategoryRVAdapter.CategoryItemViewHolder>(){
        class CategoryItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val categoryIV:ImageView = itemView.findViewById(R.id.Cat_RV_ImgV)
            val categoryTv:TextView = itemView.findViewById(R.id.Cat_RV_TextV)

    }
    interface CatogeryClickInterface{
        fun onCategoryClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.catogery_rv_item,parent,false)
        return CategoryItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
       val categoryitem = categoryList[position]
        holder.categoryTv.text = categoryitem.categoryName
        Picasso.get().load(categoryitem.catogeryImg).placeholder(R.drawable.wallpaper)
            .into(holder.categoryIV)

        holder.itemView.setOnClickListener {
            categoryClickInterface.onCategoryClick(position)
        }

    }
}