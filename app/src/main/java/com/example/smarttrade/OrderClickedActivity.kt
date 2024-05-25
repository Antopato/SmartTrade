package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.OnOrderClickedAdapter
import com.example.smarttrade.classes.Order
import com.example.smarttrade.databinding.OrderClickedBinding

class OrderClickedActivity : AppCompatActivity() {

    lateinit var binding : OrderClickedBinding
    val service = BusinessLogic()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.order_clicked)
        binding = OrderClickedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val order = intent.getSerializableExtra("order") as Order
        val list = service.getProductsByOrder(order.order_id)

        val adapter = OnOrderClickedAdapter(this, list)
        binding.recyclerViewProducts.adapter = adapter
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, MyOrdersActivity::class.java)
            intent.putExtra("user", order.client)
            startActivity(intent)
        }
    }
}