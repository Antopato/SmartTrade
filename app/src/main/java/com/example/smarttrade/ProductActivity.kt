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
import com.example.smarttrade.databinding.ProductPageBinding

class ProductActivity : AppCompatActivity() {

    lateinit var binding : ProductPageBinding
    lateinit var adapter : PriceAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ProductPageBinding.inflate(layoutInflater)

        val service = BusinessLogic()
        setContentView(binding.root)
        binding.addCarButt.isEnabled=false
        val user= intent.getSerializableExtra("user") as User
        val byteArray = intent.getByteArrayExtra("image")
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        val product = intent.getSerializableExtra("product") as Product

        var list = service.getCategoryProducts(product.productType)
        val recycler = findViewById<RecyclerView>(R.id.recycled_price)

        binding.backButt.setOnClickListener(){
            val intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
        //var bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lavadora)

        binding.nameText.text=product.name
        binding.descriptionTex.text=product.description
        binding.image.setImageBitmap(bitmap)

        adapter = PriceAdapter(this,list,user,this)

        recycler.adapter= adapter
        recycler.setLayoutManager(LinearLayoutManager(this))

        binding.addCarButt.setOnClickListener(){
            val product = adapter.selectedProduct
            service.addProductToCar(product)
        }

        binding.toWhislistButt.setOnClickListener(){
            service.addToWhislist(user,product)
        }
    }
    fun notifyButt(){
        if (adapter.selectedGlobal){
            binding.addCarButt.isEnabled=true
        }else{
            binding.addCarButt.isEnabled=false
        }
    }
}