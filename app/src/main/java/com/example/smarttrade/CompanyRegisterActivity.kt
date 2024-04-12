package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.BusinessLogic

class CompanyRegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.company_register)
        val service = BusinessLogic(this)

        val registerButt = findViewById<Button>(R.id.registerButt)

        val name = findViewById<EditText>(R.id.textName)
        val surname = findViewById<EditText>(R.id.surnameText)
        val password = findViewById<EditText>(R.id.passwordText)
        val email = findViewById<EditText>(R.id.emailText)
        val holder = findViewById<EditText>(R.id.holderText)
        val iban = findViewById<EditText>(R.id.ibanText)
        val date = findViewById<EditText>(R.id.dateText)
        val street = findViewById<EditText>(R.id.editTextStreet)
        val city = findViewById<EditText>(R.id.editTextCity)
        val province = findViewById<EditText>(R.id.editTextProvince)
        val postalCode = findViewById<EditText>(R.id.editTextPostalCode)
        val error = findViewById<TextView>(R.id.textViewError)

        val list: List<EditText> = listOf(name, surname, password, email, holder, iban, date, street, city, province, postalCode)

        registerButt.setOnClickListener(){
            val allFilled = list.all { it.text.isNotEmpty() }
            if(allFilled){
                //PUT registerMerchant
                val intent = Intent(this, CatalogActivity::class.java)
                startActivity(intent)
                error.visibility = TextView.INVISIBLE
            } else {
                error.text = "All fields must be filled"
                error.visibility = TextView.VISIBLE
            }
        }
    }
}