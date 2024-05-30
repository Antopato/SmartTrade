package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.PriceAdapter
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
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

        var  valoration = service.getValoration(product.productId)
        val recycler = findViewById<RecyclerView>(R.id.recycled_price)

        binding.pathString.text = setPath(product.productType)

        binding.backButt.setOnClickListener(){
            val intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
        //var bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lavadora)

        binding.ratingBarProduct.rating=valoration.toFloat()


        binding.nameText.text=product.name
        binding.descriptionTex.text=product.description
        binding.image.setImageBitmap(bitmap)

        adapter = PriceAdapter(this,service.getSeller(product.productId),user,this, binding.backgroundLayout)

        recycler.adapter= adapter
        recycler.setLayoutManager(LinearLayoutManager(this))


        binding.addCarButt.setOnClickListener(){
            adapter.addProdToCar()
        }

        binding.toWhislistButt.setOnClickListener(){
            service.addToWhislist(product.productId,user.email)

            val widthInPixels = 920
            val heightInPixels = 570
            val popupView = LayoutInflater.from(this).inflate(R.layout.popup_advert, null)
            val popupWindow = PopupWindow(popupView, widthInPixels, heightInPixels, true)
            val advert = popupView.findViewById<TextView>(R.id.advertText)
            val buttonAdd = popupView.findViewById<View>(R.id.buttonAdd)
            val string = advert.text.toString() + "Whislist"
            advert.text= string

            buttonAdd.setOnClickListener(){
                    popupWindow.dismiss()
                }

            popupWindow.showAtLocation(binding.backgroundLayout, Gravity.CENTER, 0, 0)
        }
    }
    fun notifyButt(){
        if (adapter.selectedGlobal){
            binding.addCarButt.isEnabled=true
        }else{
            binding.addCarButt.isEnabled=false
        }
    }

    fun setPath(type : String):String {
        var isElectronic = type == "PHONE" || type=="HOUSEHOLD" || type == "COMPUTER"
        var isFashion = type=="FASHIONTOP" || type=="FASHIONBOTTOM" || type=="FOOTWEAR"



        if (isElectronic) {
            return "/ELECTRONICS/"+type
        } else if (isFashion) {
            return "/FASHION/"+type
        } else{
            return "FOOD/"+type
        }

    }
}