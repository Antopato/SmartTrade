package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.MoneyAdapter
import com.example.smarttrade.classes.User
import com.example.smarttrade.classes.typeofusers.Merchant
import com.example.smarttrade.databinding.CopyProductBinding

class CopyProductActivity : AppCompatActivity() {

    val service = BusinessLogic()
    lateinit var binding: CopyProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.copy_product)
        binding = CopyProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("user") as User
        println(user.email)

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }

        val list = service.getProducts()
        val adapter = MoneyAdapter(this, list, user)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setLayoutManager(LinearLayoutManager(this))

        binding.buttonAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }
}