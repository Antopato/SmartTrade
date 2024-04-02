package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InitialActivity : AppCompatActivity(){

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initial_page)

        val logInButt = findViewById<Button>(R.id.logInButt)
        val regCustButt = findViewById<Button>(R.id.regCusButt)
        val regMerchButt = findViewById<Button>(R.id.reComButt)

        regCustButt.setOnClickListener() {
            println("Has pulsado register")
            startActivity(Intent(this, CostumerRegisterActivity::class.java))
        }

        logInButt.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        regMerchButt.setOnClickListener(){
            startActivity(Intent(this, MerchantRegisterActivity::class.java))
        }

    }
}