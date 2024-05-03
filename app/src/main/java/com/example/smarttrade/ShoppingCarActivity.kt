package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.CarAdapter
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.ShoppingCarBinding

class ShoppingCarActivity : AppCompatActivity() {

    val service = BusinessLogic()
    lateinit var binding : ShoppingCarBinding
    lateinit var adapter : CarAdapter
    val recyclerList = mutableListOf<ShoppingCart>()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        val user = intent.getSerializableExtra("user") as User
        var list = service.getShoppingCar(user.email)

        binding = ShoppingCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerList.addAll(list)
        adapter = CarAdapter(this,recyclerList,this, user)

        binding.recyclerPrices.adapter = adapter
        binding.recyclerPrices.setLayoutManager(LinearLayoutManager(this))

        val total = adapter.getTotal()
        binding.totalText.text = total.toString() + "€"

    }

    fun change(){
        val total = adapter.getTotal()
        binding.totalText.text = total.toString() + "€"
    }

    fun changeData(bool : Boolean, position : Int){
        recyclerList.removeAt(position)
        //var newList = service.getShoppingCar(user.email)
        //recyclerList.clear()
        //recyclerList.addAll(recyclerList)
        println(recyclerList.count())
        adapter.notifyDataSetChanged()
        change()

        val widthInPixels = 920
        val heightInPixels = 570
        val popupView = LayoutInflater.from(this).inflate(R.layout.popup_advert, null)
        val popupWindow = PopupWindow(popupView, widthInPixels, heightInPixels, true)
        val advert = popupView.findViewById<TextView>(R.id.advertText)
        val buttonAdd = popupView.findViewById<View>(R.id.buttonAdd)
        val string = advert.text.toString() + "For Later List"
        advert.text= string

        buttonAdd.setOnClickListener(){
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(binding.backgroundLayout, Gravity.CENTER, 0, 0)
    }
}