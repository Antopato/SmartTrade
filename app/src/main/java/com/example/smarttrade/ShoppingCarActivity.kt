package com.example.smarttrade

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.CarAdapter
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.ShoppingCarBinding

class ShoppingCarActivity : AppCompatActivity(), Observer {

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
        adapter = CarAdapter(this,recyclerList,this)

        binding.recyclerPrices.adapter = adapter
        binding.recyclerPrices.setLayoutManager(LinearLayoutManager(this))

        val total = adapter.getTotal()
        binding.totalText.text = total.toString() + "€"

    }

    override fun change(){
        val total = adapter.getTotal()
        binding.totalText.text = total.toString() + "€"
    }

    fun changeData(){
        //recyclerList.removeAt(position)
        println(recyclerList.count())
        adapter.notifyDataSetChanged()
        change()
    }
}