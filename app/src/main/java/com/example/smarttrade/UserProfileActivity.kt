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

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }

        /*binding.buttonWishList.setOnClickListener {
            val intent = Intent(this, WishListActivity::class.java)
            startActivity(intent)
        }*/

        /*binding.buttonForLaterList.setOnClickListener {
            val intent = Intent(this, ForLaterListActivity::class.java)
            startActivity(intent)
        }*/
    }
}