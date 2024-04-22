package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.classes.typeofusers.Costumer
import com.example.smarttrade.databinding.CostumerRegisterBinding

class CostumerRegisterActivity : AppCompatActivity(){

    lateinit var binding : CostumerRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = CostumerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = BusinessLogic()
        val type = "CLIENT"

        val balance = 0

        val buttonRegisterCostumer = binding.buttonRegisterCostumer
        val name = binding.editTextName
        val surname = binding.editTextSurname
        val email = binding.editTextMail
        val password = binding.editTextPassword
        val nif = binding.editTextNif
        val street = binding.editTextStreet
        val city = binding.editTextCity
        val postalCode = binding.editTextPostalCode
        val province = binding.editTextProvince
        val error = binding.textViewError
        val birthdate = binding.editTextBirthDate

        val list = listOf(name, surname, email, password, nif, street, city, postalCode, province, birthdate)


        buttonRegisterCostumer.setOnClickListener(){
            val allFilled = list.all { it.text.isNotEmpty() }
            val costumer = Costumer(name.text.toString() + " " + surname.text.toString(), password.text.toString(), email.text.toString(), type, birthdate.text.toString(), balance)
            if(allFilled){
                service.createCostumer(costumer)
                val intent = Intent(this, CatalogActivity::class.java)
                startActivity(intent)
                error.visibility = TextView.INVISIBLE
            }else{
                error.text = "All fields must be filled"
                error.visibility = TextView.VISIBLE
            }
        }



    }
}