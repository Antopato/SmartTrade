package com.example.smarttrade

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.AddressAdapter
import com.example.smarttrade.classes.Address
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
        binding.errorAddressText.visibility = View.INVISIBLE
        val list = service.getAdresses(user.email)
        val adapter = AddressAdapter(list,this)
        binding.addressRecyclerView.adapter = adapter
        binding.addressRecyclerView.setLayoutManager(LinearLayoutManager(this))



        binding.backToInfo.setOnClickListener(){
            val intent = Intent(this, CartInfoActivity::class.java)
            intent.putExtra("user",user)
            intent.putExtra("price", price)
            startActivity(intent)

        }

        binding.toCardButt.setOnClickListener(){
            if(!adapter.selection) {
                val street = binding.streetTextView.text.toString()
                val city = binding.cityEditText.text.toString()
                val province = binding.provinceEditText.text.toString()
                val code = binding.postalCodeEditText.text.toString()

                if(street == "" || city=="" || province=="" || code == ""){
                    //ERROR Hay que seleccionar o añadir una dirección
                    binding.errorAddressText.visibility = View.VISIBLE
                }else{
                    val address = Address(0,city,code.toInt(),province,street,user.email)
                    service.addAddress(address)
                    val intent = Intent(this, SelectCreditCardActivity::class.java)
                    intent.putExtra("user",user)
                    intent.putExtra("address", address)
                    startActivity(intent)
                }
            }else{
                val address = adapter.selectedAddress
                println("paso a la siguiente pagina")
                val intent = Intent(this, SelectCreditCardActivity::class.java)
                intent.putExtra("user",user)
                intent.putExtra("address", address)
                startActivity(intent)

            }
        }
    }
}