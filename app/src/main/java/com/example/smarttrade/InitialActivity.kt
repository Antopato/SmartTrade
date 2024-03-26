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
        val regComButt = findViewById<Button>(R.id.regComButt)

        regCustButt.setOnClickListener() {
            startActivity(Intent(this, CostumerRegisterActivity::class.java))
        }

        logInButt.setOnClickListener(){
            startActivity(Intent(this, LogInActivity::class.java))
        }

        regComButt.setOnClickListener(){
            startActivity(Intent(this, CompanyRegisterActivity::class.java))
        }

    }
}