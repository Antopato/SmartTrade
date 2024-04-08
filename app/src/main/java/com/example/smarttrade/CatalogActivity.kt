package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.ProductsAdapter
import com.example.smarttrade.adapters.RecyclerViewInterface
import com.example.smarttrade.classes.Product
import java.io.File
import java.io.FileInputStream
import java.sql.Blob

class CatalogActivity : AppCompatActivity(), RecyclerViewInterface {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val service = BusinessLogic()
        setContentView(R.layout.catalog_page)


        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        var list = service.getProduct(this)

        val adapter : ProductsAdapter = ProductsAdapter(this, list, this)

        recycler.adapter= adapter

        recycler.setLayoutManager(LinearLayoutManager(this))

        val buttonAddProduct = findViewById<Button>(R.id.buttonAddProduct)
        buttonAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        val buttonCertificate = findViewById<Button>(R.id.buttonCertificate)
        buttonCertificate.setOnClickListener {
            val intent = Intent(this, CertificateRequestsActivity::class.java)
            startActivity(intent)
        }





    }


}