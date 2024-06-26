package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarttrade.adapters.CarAdapter
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.classes.User
import com.example.smarttrade.databinding.ShoppingCarBinding

class ShoppingCarActivity : AppCompatActivity() {

    private lateinit var binding: ShoppingCarBinding
    private lateinit var adapter: CarAdapter
    private val recyclerList = mutableListOf<ShoppingCart>()
    private val movedToLaterList = mutableListOf<ShoppingCart>()
    private val service = BusinessLogic()
    private val originator = Originator()
    private val caretaker = CareTaker(originator)
    private var initialState: List<ShoppingCart> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent.getSerializableExtra("user") as User
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

        binding.undoBtn.setOnClickListener {
            undoChange(user)
        }
    }

    fun saveState() {
        caretaker.addMemento(originator.guardar())
    }

    private fun undoChange(user : User) {
        caretaker.undo()
        for(shoppingCart in caretaker.getLasMemento()){
            println("cantidades del producto: ${shoppingCart.quantity}")
            service.updateQuantity(shoppingCart.shoppingCart_id, shoppingCart.quantity)
        }
        recyclerList.clear()
        recyclerList.addAll(originator.getState())
        adapter.notifyDataSetChanged()
        change()


        for (product in movedToLaterList) {
            var sell = service.getSellById(product.product_id)
            service.deleteProdFromLater(product.product_id, user.email)
            service.addProductToCar(sell!!, product.shopping_cart_owner)
        }
        movedToLaterList.clear()
    }

    private fun undoAllChanges(user: User) {
        originator.setState(initialState)
        recyclerList.clear()
        recyclerList.addAll(initialState)
        adapter.notifyDataSetChanged()
        change()
        for (product in movedToLaterList) {
            var sell = service.getSellById(product.product_id)
            service.deleteProdFromLater(product.product_id, user.email)
            service.addProductToCar(sell!!, product.shopping_cart_owner)
        }
        movedToLaterList.clear()
    }

    fun change() {
        val total = adapter.getTotal()
        binding.totalText.text = "$total€"
    }

    fun changeData(product: ShoppingCart, position: Int, forLater:Boolean) {
        saveState()
        recyclerList.removeAt(position)
        movedToLaterList.add(product)
        adapter.notifyDataSetChanged()
        change()
        if(forLater){
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
