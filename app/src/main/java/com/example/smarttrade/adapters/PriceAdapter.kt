package com.example.smarttrade.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.Observer
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.User


class PriceAdapter (var context: Context, var list: List<Sell>, var user : User, var activity: ProductActivity) : RecyclerView.Adapter<PriceAdapter.PriceHolder>() {

    val holderList = mutableListOf<PriceHolder>()
    var selectedGlobal = false
    lateinit var selectedProduct : Sell
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.price_recycler, parent, false)
        val viewHold = PriceHolder(view,this,list)
        addObserver(viewHold)

        return viewHold
    }

    override fun onBindViewHolder(holder: PriceHolder, position: Int) {
        println("Vendido por "+ list.get(position).seller_email)
        holder.name.setText(list.get(position).seller_email)
        val string = list.get(position).price.toString() + "€"
        holder.price.setText(string)

        holder.itemViewP.setOnClickListener {
            holder.selectSeller()
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun addObserver(viewHold : PriceHolder){
        holderList.add(viewHold)
    }
    fun addProdToCar(){
        println("Seleccionado "+ selectedProduct.price)
        service.addProductToCar(selectedProduct, user.email)
    }

    class PriceHolder(var itemView: View,var adapter : PriceAdapter,var list:List<Sell> ) : RecyclerView.ViewHolder(itemView),
        Observer {
        val price : TextView = itemView.findViewById(R.id.price_text)
        val name : TextView = itemView.findViewById(R.id.prodName)
        var selected :Boolean = false
        val carView: CardView = itemView.findViewById(R.id.cardView)
        val itemViewP = itemView

        fun selectSeller(){
            if (!selected) {
                change()
                carView.setBackgroundColor(Color.parseColor("#8BD1EF"))
                adapter.selectedGlobal= true
                selected = true
                adapter.selectedProduct = list.get(adapterPosition)
                adapter.activity.notifyButt()
            }else {
                carView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                selected = false
                adapter.selectedGlobal = false
                adapter.activity.notifyButt()
            }
        }

        override fun change(){
            for(holder in adapter.holderList){
                holder.carView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                holder.selected=false
            }
        }
    }
}




