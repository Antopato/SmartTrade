package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.ListAdapter
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.ListsPageBinding

class ListsActivity : AppCompatActivity() {
    lateinit var binding : ListsPageBinding
    lateinit var adapter : ListAdapter
    lateinit var listProd : MutableList<Product>
    lateinit var listSell : MutableList<Sell>
    lateinit var listType :String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = BusinessLogic()
        var type = intent.getStringExtra("type")
        listType = type!!
        val user = intent.getSerializableExtra("user") as User


        binding = ListsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(listType=="whislist"){
            binding.listTypeText.text="Your Whislist"
            listProd = service.getWhislist(user.email)
            adapter = ListAdapter(this, listProd,null , listType!!, user,this)

        }else{
            binding.listTypeText.text="Stored for later"
            listSell=service.getForLaterList(user.email)
            adapter = ListAdapter(this, null, listSell ,listType.toString(), user, this)

        }

        binding.recyclerList.adapter = adapter
        binding.recyclerList.setLayoutManager(LinearLayoutManager(this))


        binding.deleteAllButt.setOnClickListener{
            if(listType=="whislist"){
                service.deleteAllWhislist(user.email)
                listProd.clear()
                adapter.notifyDataSetChanged()

            }else{
                service.deleteAllForLater(user.email)
                listSell.clear()
                adapter.notifyDataSetChanged()

                val widthInPixels = 920
                val heightInPixels = 570
                val popupView = LayoutInflater.from(this).inflate(R.layout.popup_advert, null)
                val popupWindow = PopupWindow(popupView, widthInPixels, heightInPixels, true)
                val advert = popupView.findViewById<TextView>(R.id.advertText)
                val buttonAdd = popupView.findViewById<View>(R.id.buttonAdd)
                val string = advert.text.toString() + "Shopping Cart List"
                advert.text= string

                buttonAdd.setOnClickListener(){
                    popupWindow.dismiss()
                }

                popupWindow.showAtLocation(binding.backgroundLayout, Gravity.CENTER, 0, 0)

            }
        }

        binding.backButt.setOnClickListener{
            val userProfile = Intent(this, UserProfileActivity::class.java)
            userProfile.putExtra("user", user)
            startActivity(userProfile)
        }

    }

    fun refreshList(position : Int){
        if(listType=="whislist"){
            listProd.removeAt(position)
        }else{
            listSell.removeAt(position)
        }
        adapter.notifyDataSetChanged()
    }

}