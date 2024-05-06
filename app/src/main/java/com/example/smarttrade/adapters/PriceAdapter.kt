package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.Observer
import com.example.smarttrade.PopUpAdvertActivity
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.Subject
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.User


class PriceAdapter (var context: Context, var list: List<Sell>, var user : User, var activity: ProductActivity, val view : View) :
    RecyclerView.Adapter<PriceAdapter.PriceHolder>() {

    var selectedGlobal = false
    lateinit var selectedProduct : Sell
    val service = BusinessLogic()
    val listHolders = mutableListOf<PriceHolder>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.price_recycler, parent, false)
        val viewHold = PriceHolder(view,this,list)
        return viewHold
    }

    override fun onBindViewHolder(holder: PriceHolder, position: Int) {
        this.listHolders.add(holder)
        println("Debería haberse añadido a lista")
        println("Vendido por "+ list.get(position).seller_email)
        holder.name.setText(list.get(position).seller_email)
        val string = list.get(position).price.toString() + "€"
        holder.price.setText(string)

        holder.itemViewP.setOnClickListener {
            holder.selectSeller()
            addAll()
        }
    }

    fun addAll(){
        println("Has llamado a AddAll "+ listHolders.count())
        for(holder in listHolders){
            for(observer in listHolders){
                if(holder!=observer){
                    holder.addObserver(observer)
                }
            println("Obserber y holder")
            }
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }


    fun addProdToCar(){
        println("Seleccionado "+ selectedProduct.price)
        service.addProductToCar(selectedProduct, user.email)
        val widthInPixels = 920
        val heightInPixels = 570
        val popupView = LayoutInflater.from(context).inflate(R.layout.popup_advert, null)
        val popupWindow = PopupWindow(popupView, widthInPixels, heightInPixels, true)
        val advert = popupView.findViewById<TextView>(R.id.advertText)
        val buttonAdd = popupView.findViewById<View>(R.id.buttonAdd)
        val string = advert.text.toString() + "Shopping Cart"
        advert.text= string

        buttonAdd.setOnClickListener(){
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }



    class PriceHolder(var itemView: View,var adapter : PriceAdapter,var list:List<Sell> ) : RecyclerView.ViewHolder(itemView),
        Observer, Subject{
        override var listObservers = mutableListOf<Observer>()
        val price : TextView = itemView.findViewById(R.id.price_text)
        val name : TextView = itemView.findViewById(R.id.prodName)
        var selected :Boolean = false
        val carView: CardView = itemView.findViewById(R.id.cardView)
        val itemViewP = itemView

        override fun notifyObservers(){
            for(holder in listObservers){
                println("observers updated")
                holder.update()
            }
        }

        override fun addObserver(observer: Observer) {
            listObservers.add(observer)
            println("ObserverAñadido")
        }

        override fun removeObserver(observer : Observer) {
            listObservers.remove(observer)
        }

        override fun update() {
            println("Cambio de color")
            this.carView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            this.selected = false
        }

        fun selectSeller(){
            if (!selected) {
                notifyObservers()
                println("observers notificados")
                carView.setBackgroundColor(Color.parseColor("#8BD1EF"))
                adapter.selectedGlobal= true
                selected = true
                adapter.selectedProduct = list.get(adapterPosition)
                adapter.activity.notifyButt()
            }else{
                carView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                selected = false
                adapter.selectedGlobal = false
                adapter.activity.notifyButt()
            }
        }
    }
}




