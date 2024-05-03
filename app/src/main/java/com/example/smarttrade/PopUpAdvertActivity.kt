package com.example.smarttrade

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.databinding.CopyProductBinding
import com.example.smarttrade.databinding.PopupAdvertBinding

class PopUpAdvertActivity : AppCompatActivity() {
    lateinit var binding: PopupAdvertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PopupAdvertBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val text = intent.getStringExtra("text")
        binding = PopupAdvertBinding.inflate(layoutInflater)

        val string =binding.advertText.text.toString() +""+ text
        binding.advertText.text = string

        binding.buttonAdd.setOnClickListener(){
            finish()
        }
    }
}