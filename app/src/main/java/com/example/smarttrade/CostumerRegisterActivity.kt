package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.classes.typeofusers.Costumer

class CostumerRegisterActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.costumer_register)

        val service = BusinessLogic()
        val type = "CLIENT"

        val balance = 0

        val buttonRegisterCostumer = findViewById<Button>(R.id.buttonRegisterCostumer)
        val name = findViewById<EditText>(R.id.editTextName)
        val surname = findViewById<EditText>(R.id.editTextSurname)
        val email = findViewById<EditText>(R.id.editTextMail)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val nif = findViewById<EditText>(R.id.editTextNif)
        val street = findViewById<EditText>(R.id.editTextStreet)
        val city = findViewById<EditText>(R.id.editTextCity)
        val postalCode = findViewById<EditText>(R.id.editTextPostalCode)
        val province = findViewById<EditText>(R.id.editTextProvince)
        val error = findViewById<TextView>(R.id.textViewError)
        val birthdate = findViewById<EditText>(R.id.editTextBirthDate)

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