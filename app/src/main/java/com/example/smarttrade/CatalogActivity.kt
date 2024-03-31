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
        val service = BusinessLogic()
        setContentView(R.layout.catalog_page)


        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        var list = service.getProduct(this)

        val adapter : ProductsAdapter = ProductsAdapter(this, list)

        recycler.adapter= adapter

        recycler.setLayoutManager(LinearLayoutManager(this))

        





    }


}