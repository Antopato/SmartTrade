package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.classes.typeofusers.Costumer
import com.example.smarttrade.databinding.CostumerRegisterBinding
import java.text.SimpleDateFormat
import java.util.regex.Pattern

class CostumerRegisterActivity : AppCompatActivity() {

    lateinit var binding: CostumerRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
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

        val list = listOf(
            name,
            surname,
            email,
            password,
            nif,
            street,
            city,
            postalCode,
            province,
            birthdate
        )


        buttonRegisterCostumer.setOnClickListener() {
            val allFilled = list.all { it.text.isNotEmpty() }
            val costumer = Costumer(
                name.text.toString() + " " + surname.text.toString(),
                password.text.toString(),
                email.text.toString(),
                type,
                birthdate.text.toString(),
                balance
            )
            if (ceckInputs()) {
                val user = service.createCostumer(costumer)

                val intent = Intent(this, CatalogActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
                error.visibility = TextView.INVISIBLE
            } else {
                error.text = "All fields must be filled"
                error.visibility = TextView.VISIBLE
            }
        }

    }
    fun ceckInputs(): Boolean {
        val namePattern = "^[a-zA-Z]+$"
        val emailPattern = "^[A-Za-z0-9._%+-]+@gmail\\.[A-Za-z]{2,6}$"
        val nifPattern = "^[0-9]{8}[A-Za-z]$"
        val postalCodePattern = "^[0-9]{5}$"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormat.isLenient = false
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

        when {
            name.text.isEmpty() || !Pattern.matches(namePattern, name.text) -> {
                error.text = "Name cannot be empty and must contain only letters."
                return false
            }

            surname.text.isEmpty() -> {
                error.text = "Surname cannot be empty."
                return false
            }

            email.text.isEmpty() || !Pattern.matches(emailPattern, email.text) -> {
                error.text =
                    "Email must be in the format example@gmail.com and cannot be empty."
                return false
            }

            password.text.isEmpty() -> {
                error.text = "Password cannot be empty."
                return false
            }

            nif.text.isEmpty() || !Pattern.matches(nifPattern, nif.text) -> {
                error.text = "NIF must be 8 numbers followed by a letter and cannot be empty."
                return false
            }

            street.text.isEmpty() -> {
                error.text = "Street cannot be empty."
                return false
            }

            city.text.isEmpty() -> {
                error.text = "City cannot be empty."
                return false
            }

            postalCode.text.isEmpty() || !Pattern.matches(
                postalCodePattern,
                postalCode.text
            ) -> {
                error.text = "Postal code must be 5 numbers and cannot be empty."
                return false
            }

            province.text.isEmpty() -> {
                error.text = "Province cannot be empty."
                return false
            }

            birthdate.text.isEmpty() -> {
                error.text = "Birthdate cannot be empty."
                return false
            }

            else -> {
                try {
                    dateFormat.parse(birthdate.text.toString())
                } catch (e: Exception) {
                    error.text = "Birthdate must be in the format yyyy-MM-dd."
                    return false
                }
            }
        }
        return true
    }
}