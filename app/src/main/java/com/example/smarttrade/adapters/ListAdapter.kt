package com.example.smarttrade.adapters

import android.app.ListActivity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.ListsActivity
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import java.io.ByteArrayOutputStream

class ListAdapter(var context : Context, val list : List<Product>, val type : String, val user : User, val activity : ListsActivity) : RecyclerView.Adapter<ListAdapter.ListHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.lists_page_row, parent, false)
        val viewHold = ListHolder(view, context, list, user)

        return viewHold
    }


    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        println(list.get(position).name)
        holder.name.text = list.get(position).name
        holder.description.text = list.get(position).description
        setImage(holder,list.get(position))

        holder.deleteButt.setOnClickListener(){
            if(type=="whislist"){
                service.deleteProdFromWhislist(list.get(position).productId, user.email)
                activity.refreshList(position)
            }else{
                service.deleteProdFromLater(list.get(position).productId, user.email)
                activity.refreshList(position)
            }
        }

        holder.layoutProd.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("product", list.get(position))
            intent.putExtra("user", user)

            val bitmap = (holder.image.drawable).toBitmap()
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            intent.putExtra("image", byteArray)

            context.startActivity(intent)
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


    class ListHolder(var itemView: View, var context : Context, var list : List<Product>, val user : User) :  RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.listProductName)
        val description = itemView.findViewById<TextView>(R.id.listProductDescription)
        val image = itemView.findViewById<ImageView>(R.id.listProductImage)
        val deleteButt = itemView.findViewById<Button>(R.id.listDeleteButton)
        val layoutProd = itemView.findViewById<ConstraintLayout>(R.id.listProductLayout)

    }
}