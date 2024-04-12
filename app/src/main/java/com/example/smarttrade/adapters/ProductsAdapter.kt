package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Product

class ProductsAdapter(var context: Context, var list: List<Product>) : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =  inflater.inflate(R.layout.recycler_row, parent, false)
        val viewHold = MyViewHolder(view,context,list)

        return viewHold
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.desc.setText(list.get(position).Description)
        //holder.name.setText(list.get(position).Name)
        //holder.image.setImageBitmap(list.get(position).img)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class MyViewHolder(itemView: View,context:Context,list:List<Product>) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.product_image)
        val name : TextView = itemView.findViewById(R.id.product_name)
        val desc : TextView = itemView.findViewById(R.id.product_desc);

        init{
            itemView.setOnClickListener {
                val intent = Intent(context, ProductActivity::class.java)

                //intent.putExtra("name",list.get(adapterPosition).Name)
                //intent.putExtra("desc",list.get(adapterPosition).Description)
                //intent.putExtra("image",list.get(adapterPosition).img.toString())

                context.startActivity(intent)


            }
        }




    }

}



