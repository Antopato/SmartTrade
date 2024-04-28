package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.ListAdapter
import com.example.smarttrade.classes.Product
import com.example.smarttrade.databinding.ListsPageBinding

class ListsActivity : AppCompatActivity() {
    lateinit var binding : ListsPageBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = BusinessLogic()
        val listType = intent.getStringExtra("type")
        val user = intent.getSerializableExtra("user")
        val list : List<Product>
        var adapter : ListAdapter


        binding = ListsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(listType=="whislist"){
            binding.listTypeText.text="Your Whislist"
            list = service.getWhislist()
            adapter = ListAdapter(this, list, listType)

        }else{
            binding.listTypeText.text="Stored for later"
            list =service.getForLaterList()
            adapter = ListAdapter(this, list, listType.toString())

        }

        binding.recyclerList.adapter = adapter
        binding.recyclerList.setLayoutManager(LinearLayoutManager(this))




        binding.backButt.setOnClickListener{
            val userProfile = Intent(this, UserProfileActivity::class.java)
            userProfile.putExtra("user", user)
            startActivity(userProfile)
        }

    }
}