package com.example.smarttrade

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.MyOrdersAdapter
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.MyOrdersBinding

class MyOrdersActivity : AppCompatActivity() {

    lateinit var binding : MyOrdersBinding
    val service = BusinessLogic()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.my_orders)
        binding = MyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("user") as User

        val ordersList = service.getOrdersById(user.email)

        val adapter = MyOrdersAdapter(this, ordersList)
        binding.recyclerViewOrders.adapter = adapter
        binding.recyclerViewOrders.layoutManager = LinearLayoutManager(this)

    }
}