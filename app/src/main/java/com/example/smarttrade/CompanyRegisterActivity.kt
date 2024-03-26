package com.example.smarttrade

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.BusinessLogic

class CompanyRegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.company_register)
        val service = BusinessLogic()

        val registerButt = findViewById<TextView>(R.id.registerButt);

        registerButt.setOnClickListener(){
            var name = findViewById<TextView>(R.id.textName).text.toString()
            var surname = findViewById<TextView>(R.id.surnameText).text.toString()
            var pass = findViewById<TextView>(R.id.passwordText).text.toString()
            var email = findViewById<TextView>(R.id.emailText).text.toString()
            var holder = findViewById<TextView>(R.id.holderText).text.toString()
            var iban = findViewById<TextView>(R.id.ibanText).text.toString()
            var date = findViewById<TextView>(R.id.dateText).text.toString()

            service.registerCompany(name,surname,email,pass,holder,iban,date)

        }
    }
}