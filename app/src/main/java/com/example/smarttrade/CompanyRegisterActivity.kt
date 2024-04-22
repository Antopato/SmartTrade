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
import com.example.smarttrade.databinding.CompanyRegisterBinding

class CompanyRegisterActivity : AppCompatActivity() {

    lateinit var binding : CompanyRegisterBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = CompanyRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val service = BusinessLogic()

        val registerButt = findViewById<Button>(R.id.registerButt)

        val name = binding.textName
        val surname = binding.surnameText
        val email = binding.emailText
        val password = binding.passwordText
        val holder = binding.holderText
        val iban = binding.ibanText
        val date = binding.dateText
        val street = binding.editViewStreet
        val city = binding.editTextCity
        val province = binding.editViewProvince
        val postalCode = binding.editTextPostalCode
        val error = binding.textViewError

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