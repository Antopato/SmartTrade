package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.UserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    lateinit var binding: UserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = UserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("user") as User

        binding.apply {
            textViewNameInput.text = user.name
            textViewEmailInput.text = user.email
            textViewPasswordInput.text = user.password
        }

        binding.catalogImage.setOnClickListener {
            val back = Intent(this, CatalogActivity::class.java)
            back.putExtra("user",user)
            startActivity(back)
        }

        binding.imageViewCart.setOnClickListener(){
            val cart = Intent(this, ShoppingCarActivity::class.java)
            cart.putExtra("user",user)
            startActivity(cart)
        }

        binding.buttonWishList.setOnClickListener {
            val whislist = Intent(this, ListsActivity::class.java)
            whislist.putExtra("user",user)
            whislist.putExtra("type", "whislist")
            startActivity(whislist)
        }

        binding.buttonForLaterList.setOnClickListener {
            val forLater = Intent(this, ListsActivity::class.java)
            forLater.putExtra("user",user)
            forLater.putExtra("type", "forLater")
            startActivity(forLater)
        }

        binding.buttonOrders.setOnClickListener {
            val orders = Intent(this, MyOrdersActivity::class.java)
            orders.putExtra("user",user)
            startActivity(orders)
        }
    }
}