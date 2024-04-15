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
import com.example.smarttrade.classes.typeofusers.Merchant

class CompanyRegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.company_register)
        val service = BusinessLogic()

        val registerButt = findViewById<Button>(R.id.registerButt)

        val name = findViewById<EditText>(R.id.textName)
        val surname = findViewById<EditText>(R.id.surnameText)
        val email = findViewById<EditText>(R.id.emailText)
        val password = findViewById<EditText>(R.id.passwordText)
        val holder = findViewById<EditText>(R.id.holderText)
        val iban = findViewById<EditText>(R.id.ibanText)
        val date = findViewById<EditText>(R.id.dateText)
        val street = findViewById<EditText>(R.id.editViewStreet)
        val city = findViewById<EditText>(R.id.editTextCity)
        val province = findViewById<EditText>(R.id.editViewProvince)
        val postalCode = findViewById<EditText>(R.id.editTextPostalCode)
        val error = findViewById<TextView>(R.id.textViewError)

        val list: List<EditText> = listOf(name, surname, password, email, holder, iban, date, street, city, province, postalCode)

        registerButt.setOnClickListener(){
            val allFilled = list.all { it.text.isNotEmpty() }
            val merchant = Merchant(name.text.toString() + " " + surname.text.toString(),password.text.toString(), email.text.toString(), "MERCHANT", date.text.toString(), holder.text.toString())
            if(allFilled){
                service.createMerchant(merchant)
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