package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.CartInfoAdapter
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.BuyInformationPageBinding

class CartInfoActivity : AppCompatActivity() {

    lateinit var binding : BuyInformationPageBinding
    var service = BusinessLogic()

    override fun onCreate(savedInstanceState: Bundle?){
        var user = intent.getSerializableExtra("user") as User
        var price = intent.getIntExtra("price",0)
        super.onCreate(savedInstanceState)
        binding = BuyInformationPageBinding.inflate(layoutInflater)
        var list = service.getShoppingCar(user.email)
        setContentView(binding.root)

        binding.customerTextView.text = user.name
        binding.priceTextView.text = "${price}€"

        var adapter = CartInfoAdapter(this,list)
        binding.cartInfoRecyclerView.adapter = adapter
        binding.cartInfoRecyclerView.setLayoutManager(LinearLayoutManager(this))

        binding.toCardButton.setOnClickListener(){
            val intent = Intent(this, SelectAddressActivity::class.java)
            intent.putExtra("price", price)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        binding.buttonBackToCart.setOnClickListener(){
            val intent = Intent(this, ShoppingCarActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

    }
}