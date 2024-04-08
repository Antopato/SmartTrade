package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Price
import com.example.smarttrade.classes.Product


class PriceAdapter (var context: Context, var list: List<Price>) : RecyclerView.Adapter<PriceAdapter.PriceHolder>(), RecyclerViewInterface {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.price_recycler, parent, false)
        val viewHold = PriceHolder(view,  context, list)

        return viewHold
    }

    override fun onBindViewHolder(holder: PriceHolder, position: Int) {
        holder.company.setText(list.get(position).company)
        val string = list.get(position).price.toString() + "€"
        holder.price.setText(string)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class PriceHolder(itemView: View, context: Context, list: List<Price>) : RecyclerView.ViewHolder(itemView) {
        val company : TextView = itemView.findViewById(R.id.company_name)
        val price : TextView = itemView.findViewById(R.id.price_text)
        var selected :Boolean = false

        init{
            itemView.setOnClickListener {
                val carView : CardView = itemView.findViewById(R.id.cardView)
                if(!selected) {
                    carView.setBackgroundColor(Color.parseColor("#8BD1EF"))
                    selected =true
                }else{
                    carView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    selected=false
                }

            }

        }
    }


}

