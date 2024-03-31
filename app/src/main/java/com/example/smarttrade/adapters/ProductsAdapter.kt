package com.example.smarttrade.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.R
import com.example.smarttrade.classes.Product

class ProductsAdapter(var context: Context, var list: List<Product>) : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =  inflater.inflate(R.layout.recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.desc.setText(list.get(position).Description)
        holder.name.setText(list.get(position).Name)
        holder.image.setImageBitmap(list.get(position).img)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.product_image)
        val name : TextView = itemView.findViewById(R.id.product_name)
        val desc : TextView = itemView.findViewById(R.id.product_desc)
    }
}

