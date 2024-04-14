package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.PriceAdapter
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User

class ProductActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val user= intent.getSerializableExtra("user") as User
        //val image = intent.getSerializableExtra("image") as BitmapDrawable
        val service = BusinessLogic()
        setContentView(R.layout.product_page)

        val product = intent.getSerializableExtra("product") as Product

        var list = service.getCategoryProducts(product.productType)

        val nametext = findViewById<TextView>(R.id.nameText)
        val desctext = findViewById<TextView>(R.id.descriptionTex)
        val imageview = findViewById<ImageView>(R.id.image)
        val recycler = findViewById<RecyclerView>(R.id.recycled_price)
        val backbutt = findViewById<Button>(R.id.backButt)

        backbutt.setOnClickListener(){
            val intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
        var bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lavadora)

        nametext.text=product.name
        desctext.text=product.description
        //imageview.setImageBitmap(image.bitmap)

        val adapter = PriceAdapter(this,list,user)

        recycler.adapter= adapter

        recycler.setLayoutManager(LinearLayoutManager(this))
    }
}