package com.example.wallpaperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call as Call

class MainActivity : AppCompatActivity(),CategoryRVAdapter.CatogeryClickInterface {

    lateinit var wallpaperRv: RecyclerView
    lateinit var categoryRv: RecyclerView
    lateinit var categoryRVAdapter: CategoryRVAdapter
    lateinit var wallpaperRVAdapter: WallpaperRVAdapter
    lateinit var loadingPB: ProgressBar
    lateinit var searchEdt: EditText
    lateinit var searchIV: ImageView
    lateinit var WallpaperList:List<String>
    lateinit var CategoryList: List<CatogeryRVModel>
    lateinit var retrofitAPI: RetrofitAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = getColor(R.color.black_shade_1)

        wallpaperRv = findViewById(R.id.RVWallpapers)
        categoryRv = findViewById(R.id.RVCatogery)
        loadingPB =findViewById(R.id.Loading)
        searchEdt = findViewById(R.id.edtSearch)
        searchIV = findViewById(R.id.serchicon)

        WallpaperList = ArrayList<String>()
        CategoryList = ArrayList<CatogeryRVModel>()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        searchIV.setOnClickListener{
            if (searchEdt.text.toString().isNotEmpty()){
                getWallpaperByCategory(retrofitAPI,searchEdt.text.toString())

            }
        }
        getWallpaperByCategory(retrofitAPI, "")
        getCategories()





    }
    private fun getCategories(){
        CategoryList = CategoryList + CatogeryRVModel("Nature","https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")

        CategoryList = CategoryList + CatogeryRVModel("Architecture","https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
        CategoryList = CategoryList + CatogeryRVModel("Arts","https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
        CategoryList = CategoryList + CatogeryRVModel("Music","https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
        CategoryList = CategoryList + CatogeryRVModel("Abstract","https://images.pexels.com/photos/2110951/pexels-photo-2110951.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
        CategoryList = CategoryList + CatogeryRVModel("Cars","https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
        CategoryList = CategoryList + CatogeryRVModel("Flowers","https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")

        categoryRVAdapter = CategoryRVAdapter(CategoryList,this,this)
        categoryRv.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        categoryRv.adapter = categoryRVAdapter
        categoryRVAdapter.notifyDataSetChanged()

    }
    private fun getWallpaperByCategory(retrofitAPI: RetrofitAPI,category:String){
     var call: Call<WallpaperRVModel>? = null
        if (category.isNotEmpty()){
            call = retrofitAPI.getWallpaperByCatogery(category,30,1)
        }else{
            call = retrofitAPI.getWallpapers()
        }
        WallpaperList = ArrayList<String>()
        loadingPB.visibility = View.VISIBLE


        call!!.enqueue(object : Callback<WallpaperRVModel?>{
            override fun onResponse(
                call: Call<WallpaperRVModel?>,
                response: Response<WallpaperRVModel?>
            ) {
                if (response.isSuccessful){
                    val dt = response.body()

                    loadingPB.visibility = View.GONE
                    val photolists: ArrayList<photos> = dt!!.photos

                    if (photolists.isNotEmpty()){
                      for(i in 0 until photolists.size){
                          val photoOBJ = photolists.get(i)
                          val imgUrl: String = photoOBJ.src.portrait
                          WallpaperList = WallpaperList + imgUrl
                      }
                    }else{
                        Toast.makeText(this@MainActivity,"No Wallpaper found",Toast.LENGTH_LONG).show()

                    }
                    wallpaperRVAdapter = WallpaperRVAdapter(WallpaperList,applicationContext)
                    wallpaperRv.layoutManager = GridLayoutManager(applicationContext,2)
                    wallpaperRv.adapter = wallpaperRVAdapter
                    wallpaperRVAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@MainActivity,"Fail to get response",Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<WallpaperRVModel?>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Fail to get Wallpapers: ${t}",Toast.LENGTH_LONG)
                    .show()
            }

        })

    }

    override fun onCategoryClick(position: Int) {
        val category: String = CategoryList.get(position).categoryName
        getWallpaperByCategory(retrofitAPI, category)
    }
}