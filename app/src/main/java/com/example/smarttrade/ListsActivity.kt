package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.ListAdapter
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.ListsPageBinding

class ListsActivity : AppCompatActivity() {
    lateinit var binding : ListsPageBinding
    var list = mutableListOf<Product>()
    lateinit var adapter : ListAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = BusinessLogic()
        val listType = intent.getStringExtra("type")
        val user = intent.getSerializableExtra("user") as User


        binding = ListsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(listType=="whislist"){
            binding.listTypeText.text="Your Whislist"
            list = service.getWhislist(user.email)
            adapter = ListAdapter(this, list, listType, user,this)

        }else{
            binding.listTypeText.text="Stored for later"
            list =service.getForLaterList(user.email)
            adapter = ListAdapter(this, list, listType.toString(), user, this)

        }

        binding.recyclerList.adapter = adapter
        binding.recyclerList.setLayoutManager(LinearLayoutManager(this))


        binding.deleteAllButt.setOnClickListener{
            if(listType=="whislist"){
                service.deleteAllWhislist(user.email)
            }else{
                service.deleteAllForLater(user.email)
            }
            list.clear()
            adapter.notifyDataSetChanged()
        }

        binding.backButt.setOnClickListener{
            val userProfile = Intent(this, UserProfileActivity::class.java)
            userProfile.putExtra("user", user)
            startActivity(userProfile)
        }

    }

    fun refreshList(position : Int){
        list.removeAt(position)
        adapter.notifyDataSetChanged()
    }

}