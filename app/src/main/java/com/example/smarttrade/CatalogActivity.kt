package com.example.smarttrade

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.ProductsAdapter
import com.example.smarttrade.classes.Product
import java.io.File
import java.io.FileInputStream
import java.sql.Blob

class CatalogActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.catalog_page)


        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        var list = getProduct()

        val adapter : ProductsAdapter = ProductsAdapter(this, list)

        recycler.adapter= adapter

        recycler.setLayoutManager(LinearLayoutManager(this))







    }

    fun getProduct() : List<Product>{
        var bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lavadora)

        val prod1 = Product("Lavadora", "Lava que flipas", bitmap)
        val prod2 = Product("Lavadora", "Lava que flipas la segunda vez tb", bitmap)
        val prod3 = Product("Lavadora", "Lava que flipas la segunda vez tb1", bitmap)
        val prod4 = Product("Lavadora", "Lava que flipas la segunda vez tb2", bitmap)
        val prod5 = Product("Lavadora", "Lava que flipas la segunda vez tb3", bitmap)
        val prod6 = Product("Lavadora", "Lava que flipas la segunda vez tb4", bitmap)
        val prod7 = Product("Lavadora", "Lava que flipas la segunda vez tb5", bitmap)
        val prod8 = Product("Lavadora", "Lava que flipas la segunda vez tb6", bitmap)

        val list = mutableListOf<Product>(prod1, prod2, prod3, prod4, prod5, prod6,prod7,prod8)

        return list

    }
}