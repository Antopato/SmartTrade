package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.CreditCardAdapter
import com.example.smarttrade.classes.Address
import com.example.smarttrade.classes.CreditCard
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.SelectCreditCardBinding
import com.example.smarttrade.strategy.CheckoutStrategy
import com.example.smarttrade.strategy.PaypalStrategy
import com.example.smarttrade.strategy.Strategy

class SelectCreditCardActivity : AppCompatActivity() {

    lateinit var binding : SelectCreditCardBinding
    val service = BusinessLogic()
    lateinit var strategy: Strategy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SelectCreditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val user = intent.getSerializableExtra("user") as User
        val address = intent.getSerializableExtra("address") as Address

        val list = service.getCreditCardsByUser(user.email)
        println(list)
        val adapter = CreditCardAdapter(list, this)
        binding.creditCardRecyclerView.adapter = adapter
        binding.creditCardRecyclerView.layoutManager = LinearLayoutManager(this)


        val shoppingCar = service.getShoppingCar(user.email)
        val order = service.createOrder(user.email)

        for(car in shoppingCar){
            if (order != null) {
                service.addProductToOrder(order.order_id, car.product_id)
            }
        }



        binding.textViewError.visibility = View.INVISIBLE


        binding.backButton.setOnClickListener{
            val intent = Intent(this, SelectAddressActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        binding.buttonPayPal.setOnClickListener{
            strategy = PaypalStrategy()
            strategy.showPopup(this, user, address)
        }

        binding.buttonCheckout.setOnClickListener{
            if(!adapter.selection){
                val number = binding.editTextNumber.text.toString()
                val expirationDate = binding.editTextExpiration.text.toString()
                val cvv = binding.editTextCVV.text.toString()

                if(number == "" || expirationDate == "" || cvv == ""){
                    binding.textViewError.visibility = View.VISIBLE
                }else{
                    val creditCard = CreditCard(number, cvv.toInt(), user.name, expirationDate)
                    service.addCreditCard(creditCard)
                    strategy = CheckoutStrategy()
                    strategy.showPopup(this, user, address)
                }
            } else {
                val creditCard = adapter.selectedCreditCard
                strategy = CheckoutStrategy()
                strategy.showPopup(this, user, address)
            }

        }

    }
}