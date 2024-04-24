package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.CertificatesAdapter
import com.example.smarttrade.adapters.ProductsAdapter
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.MerchantCertificatesBinding
import java.security.cert.Certificate

class MerchantCertificatesActivity : AppCompatActivity() {

    lateinit var binding: MerchantCertificatesBinding
    val service = BusinessLogic()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val user = intent.getSerializableExtra("user") as User
        binding = MerchantCertificatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }

        binding.buttonPending.setBackgroundColor(0x34A60C)
        binding.buttonPending.setTextColor(0xFFFFFF)

        val listOfPending = service.getMerchantProductsPending(user.email)

        val recyclerList = mutableListOf<Product?>()
        recyclerList.addAll(listOfPending)

        val recycler = binding.recyclerViewMerchantCertificates
        val adapter = CertificatesAdapter(this,recyclerList,user)
        recycler.adapter = adapter
        recycler.setLayoutManager(LinearLayoutManager(this))

        binding.buttonPending.setOnClickListener {
            binding.buttonPending.setBackgroundColor(0x34A60C)
            binding.buttonPending.setTextColor(0xFFFFFF)
            binding.buttonDenied.setBackgroundColor(0xB1FA97)
            binding.buttonDenied.setTextColor(0x000000)

            val actualListOfPending = service.getMerchantProductsPending(user.email)
            recyclerList.clear()
            recyclerList.addAll(actualListOfPending)
            adapter.notifyDataSetChanged()
        }
        binding.buttonDenied.setOnClickListener {
            binding.buttonDenied.setBackgroundColor(0x34A60C)
            binding.buttonDenied.setTextColor(0xFFFFFF)
            binding.buttonPending.setBackgroundColor(0xB1FA97)
            binding.buttonPending.setTextColor(0x000000)

            val actualListOfDenied = service.getMerchantProductsDenied(user.email)
            recyclerList.clear()
            recyclerList.addAll(actualListOfDenied)
            adapter.notifyDataSetChanged()
        }
    }
}