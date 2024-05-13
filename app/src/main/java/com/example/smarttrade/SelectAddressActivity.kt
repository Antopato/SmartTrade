package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.SelectAddressPageBinding

class SelectAddressActivity : AppCompatActivity() {
    lateinit var binding : SelectAddressPageBinding
    var service = BusinessLogic()
    override fun onCreate(savedInstanceState : Bundle?){
        binding = SelectAddressPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val user = intent.getSerializableExtra("user") as User
        val price = intent.getIntExtra("price",0)
        setContentView(binding.root)

        val list = service.getAdresses(user.email)



        binding.backToInfo.setOnClickListener(){
            val intent = Intent(this, CartInfoActivity::class.java)
            intent.putExtra("user",user)
            intent.putExtra("price", price)
            startActivity(intent)

        }

        /*binding.toCardButt.setOnClickListener(){
            if(adapter.isSelected()) {
                val street = binding.streetTextView.text.toString()
                val number = binding.numberEditText.text.toString()
                val door = binding.doorEditText.text.toString()
                if(street == "" || number=="" || door==""){
                    //ERROR Hay que seleccionar o añadir una dirección
                }
            }
        }*/
    }
}