package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.MerchantOrdersAdapter
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.MerchantOrdersBinding

class MerchantOrdersActivity : AppCompatActivity() {

    lateinit var binding : MerchantOrdersBinding
    val service = BusinessLogic()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.merchant_orders)

        val user = intent.getSerializableExtra("user") as User

        binding = MerchantOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = service.getMerchantOrders(user.email)
        println(list)

        val adapter = MerchantOrdersAdapter(this, list)
        binding.recyclerViewOrders.adapter = adapter
        binding.recyclerViewOrders.layoutManager = LinearLayoutManager(this)

        binding.buttonRefresh.setOnClickListener {
            val list = service.getMerchantOrders(user.email)
            adapter.list = list
            adapter.notifyDataSetChanged()
        }
        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }
}