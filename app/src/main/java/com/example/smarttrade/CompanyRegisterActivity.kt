package com.example.smarttrade

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.BusinessLogic

class CompanyRegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.company_register)
        val service = BusinessLogic(this)

        val registerButt = findViewById<Button>(R.id.registerButt);

        registerButt.setOnClickListener(){
            var name = findViewById<EditText>(R.id.textName).text.toString()
            var surname = findViewById<EditText>(R.id.surnameText).text.toString()
            var pass = findViewById<EditText>(R.id.passwordText).text.toString()
            var email = findViewById<EditText>(R.id.emailText).text.toString()
            var holder = findViewById<EditText>(R.id.holderText).text.toString()
            var iban = findViewById<EditText>(R.id.ibanText).text.toString()
            var date = findViewById<EditText>(R.id.dateText).text.toString()

            service.registerCompany(name,surname,email,pass,holder,iban,date)

        }
    }
}