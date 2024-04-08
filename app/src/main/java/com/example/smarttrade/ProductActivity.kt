package com.example.smarttrade

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.PriceAdapter

class ProductActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val service = BusinessLogic()
        var list = service.getPrice()
        setContentView(R.layout.product_page)

        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val img = intent.getStringExtra("image")

        val nametext = findViewById<TextView>(R.id.nameText)
        val desctext = findViewById<TextView>(R.id.descriptionTex)
        val imageview = findViewById<ImageView>(R.id.image)
        val recycler = findViewById<RecyclerView>(R.id.recycled_price)


        var bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lavadora)

        nametext.text=name
        desctext.text=desc
        imageview.setImageBitmap(bitmap)

        val adapter = PriceAdapter(this,list)

        recycler.adapter= adapter

        recycler.setLayoutManager(LinearLayoutManager(this))
    }
}