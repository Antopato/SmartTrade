package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.databinding.MerchantCertificatesBinding

class MerchantCertificatesActivity : AppCompatActivity() {

    lateinit var binding: MerchantCertificatesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.merchant_certificates)
        binding = MerchantCertificatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }

        binding.buttonPending.setOnClickListener {
            binding.buttonPending.setBackgroundColor(0x34A60C)
            binding.buttonPending.setTextColor(0xFFFFFF)
            binding.buttonDenied.setBackgroundColor(0xB1FA97)
            binding.buttonDenied.setTextColor(0x000000)
        }
        binding.buttonDenied.setOnClickListener {
            binding.buttonDenied.setBackgroundColor(0x34A60C)
            binding.buttonDenied.setTextColor(0xFFFFFF)
            binding.buttonPending.setBackgroundColor(0xB1FA97)
            binding.buttonPending.setTextColor(0x000000)
        }
    }
}