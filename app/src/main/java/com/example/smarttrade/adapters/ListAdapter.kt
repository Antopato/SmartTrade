package com.example.smarttrade.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.R
import com.example.smarttrade.classes.Product
import com.example.smarttrade.databinding.ListsPageRowBinding

class ListAdapter(var context : Context, val list : List<Product>, val type : String) : RecyclerView.Adapter<ListAdapter.ListHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.price_recycler, parent, false)
        val viewHold = ListHolder(view)

        return viewHold
    }


    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.name.text = list.get(position).name
        holder.description.text = list.get(position).description
        setImage(holder,list.get(position))

        holder.deleteButt.setOnClickListener(){
            if(type=="whislist"){
                service.deleteProdFromWhislist()
            }else{
                service.deleteProdFromLater()
            }
        }
    }


    override fun getItemCount(): Int {
        return list.count()
    }

    fun setImage(holder : ListHolder, product : Product){
        val type = product.productType
        val image = service.getImageByType(type, product.productId)
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))
    }


    class ListHolder(var itemView: View) :  RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.listProductName)
        val description = itemView.findViewById<TextView>(R.id.listProductDescription)
        val image = itemView.findViewById<ImageView>(R.id.listProductImage)
        val deleteButt = itemView.findViewById<Button>(R.id.listDeleteButton)


    }
}