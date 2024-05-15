package com.example.smarttrade.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.R
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.databinding.BuyInformationRowBinding

class CartInfoAdapter(var context : Context, var list : List<ShoppingCart>) : RecyclerView.Adapter<CartInfoAdapter.MyViewHolder>() {
    var service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =  inflater.inflate(R.layout.buy_information_row, parent, false)
        val viewHold = CartInfoAdapter.MyViewHolder(view,list)

        return viewHold
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sell = service.getSellById(list.get(position).product_id)
        val owner = service.getUserById(sell!!.seller_email)
        val product = service.getProductById(sell!!.id_product)
        holder.name.setText(product.name)
        holder.price.setText("${list.get(position).price}€")
        holder.owner.setText("${owner!!.name}")
        holder.quantity.setText("x${list.get(position).quantity}")
    }
    class MyViewHolder(itemView : View, var list : List<ShoppingCart>) : RecyclerView.ViewHolder(itemView){
        var name = itemView.findViewById<TextView>(R.id.rowProdName)
        var price = itemView.findViewById<TextView>(R.id.rowPrice)
        var quantity = itemView.findViewById<TextView>(R.id.rowQuant)
        var owner = itemView.findViewById<TextView>(R.id.ownerTextView)
    }
}