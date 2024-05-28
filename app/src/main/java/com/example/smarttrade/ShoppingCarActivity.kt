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
import com.example.smarttrade.adapters.CarAdapter
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.ShoppingCarBinding
import kotlinx.serialization.Serializable

class ShoppingCarActivity : AppCompatActivity() {

    private lateinit var binding: ShoppingCarBinding
    private lateinit var adapter: CarAdapter
    private val recyclerList = mutableListOf<ShoppingCart>()

    private val originator = Originator()
    private val caretaker = CareTaker(originator)
    private var initialState: List<ShoppingCart> = listOf()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent.getSerializableExtra("user") as User
        val service = BusinessLogic()
        val list = service.getShoppingCar(user.email)

        binding = ShoppingCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerList.addAll(list)
        originator.setState(recyclerList)
        initialState = recyclerList.toList()
        adapter = CarAdapter(this, recyclerList, this, user)

        binding.recyclerPrices.adapter = adapter
        binding.recyclerPrices.layoutManager = LinearLayoutManager(this)

        val total = adapter.getTotal()
        binding.totalText.text = "$total€"

        binding.deleteAllButtShopp.setOnClickListener {
            saveState()
            service.deleteAllShoppingCart(user.email)
            recyclerList.clear()
            adapter.notifyDataSetChanged()
            change()
        }

        binding.imageViewUser.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        binding.catalogImage.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        binding.payButton.setOnClickListener {
            service.saveCarts(list)
            val intent = Intent(this, CartInfoActivity::class.java)
            intent.putExtra("user", user)
            intent.putExtra("price", adapter.getTotal())
            startActivity(intent)
        }

        // Botón deshacer
        /**binding.undoButton.setOnClickListener {
            undoChange()
        }

        // Botón deshacer todo
        binding.undoAllButton.setOnClickListener {
            undoAllChanges()
        }*/
    }

    private fun saveState() {
        caretaker.addMemento(originator.guardar())
    }

    private fun undoChange() {
        caretaker.undo()
        recyclerList.clear()
        recyclerList.addAll(originator.getState())
        adapter.notifyDataSetChanged()
        change()
    }

    private fun undoAllChanges() {
        originator.setState(initialState)
        recyclerList.clear()
        recyclerList.addAll(initialState)
        adapter.notifyDataSetChanged()
        change()
    }

    fun change() {
        val total = adapter.getTotal()
        binding.totalText.text = "$total€"
    }

    fun changeData(bool: Boolean, position: Int) {
        saveState()
        recyclerList.removeAt(position)
        adapter.notifyDataSetChanged()
        change()
        if (bool) {
            showPopup()
        }
    }

    private fun showPopup() {
        val widthInPixels = 920
        val heightInPixels = 570
        val popupView = LayoutInflater.from(this).inflate(R.layout.popup_advert, null)
        val popupWindow = PopupWindow(popupView, widthInPixels, heightInPixels, true)
        val advert = popupView.findViewById<TextView>(R.id.advertText)
        val buttonAdd = popupView.findViewById<View>(R.id.buttonAdd)
        advert.text = advert.text.toString() + "For Later List"

        buttonAdd.setOnClickListener {
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(binding.backgroundLayout, Gravity.CENTER, 0, 0)
    }
}
