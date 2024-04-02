package com.example.smarttrade

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProductActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.product_page)


        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val img = intent.getStringExtra("image")

        val decoded = Base64.decode(img,Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decoded, 0 ,decoded.size)

        val nametext = findViewById<TextView>(R.id.nameText)
        val desctext = findViewById<TextView>(R.id.descriptionTex)
        val imageview = findViewById<ImageView>(R.id.image)
        println(name)

        nametext.text=name
        desctext.text=desc
        imageview.setImageBitmap(bitmap)
    }
}