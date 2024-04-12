package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Price


class PriceAdapter (var context: Context, var list: List<Price>) : RecyclerView.Adapter<PriceAdapter.PriceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.price_recycler, parent, false)
        val viewHold = PriceHolder(view,  context, list)

        return viewHold
    }

    override fun onBindViewHolder(holder: PriceHolder, position: Int) {
        //holder.company.setText(list.get(position).company)
        //val string = list.get(position).price.toString() + "€"
        //holder.price.setText(string)
        //holder.name.setText(list.get(position).name)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class PriceHolder(itemView: View, context: Context, list: List<Price>) : RecyclerView.ViewHolder(itemView) {
        val company : TextView = itemView.findViewById(R.id.company_name)
        val price : TextView = itemView.findViewById(R.id.price_text)
        val name : TextView = itemView.findViewById(R.id.prodName)
        var selected :Boolean = false

        init{
            itemView.setOnClickListener {
                val intent = Intent(context, ProductActivity::class.java)

                //intent.putExtra("name",list.get(adapterPosition).name)
                //intent.putExtra("desc","description")



                context.startActivity(intent)


                /*
                val carView : CardView = itemView.findViewById(R.id.cardView)

                if(!selected) {
                    carView.setBackgroundColor(Color.parseColor("#8BD1EF"))
                    selected =true
                }else{
                    carView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    selected=false
                }
                */

            }

        }
    }


}

