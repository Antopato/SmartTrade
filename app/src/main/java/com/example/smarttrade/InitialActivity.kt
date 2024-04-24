package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.databinding.InitialPageBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InitialActivity : AppCompatActivity(){

    lateinit var binding : InitialPageBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = InitialPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.regCusButt.setOnClickListener() {
            println("Has pulsado register")
            startActivity(Intent(this, CostumerRegisterActivity::class.java))
        }

       binding.logInButt.setOnClickListener(){
            startActivity(Intent(this, LogInActivity::class.java))
        }

        binding.regComButt.setOnClickListener(){
            startActivity(Intent(this, CompanyRegisterActivity::class.java))
        }
    }
}