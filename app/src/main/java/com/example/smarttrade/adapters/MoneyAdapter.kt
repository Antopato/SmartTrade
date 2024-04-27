package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.CatalogActivity
import com.example.smarttrade.CertificateValidationActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import java.io.ByteArrayOutputStream

class MoneyAdapter(var context: Context, var list: List<Product?>, var user: User) : RecyclerView.Adapter<MoneyAdapter.MoneyHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyHolder {
        val inflater : LayoutInflater  = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.recycler_row_certificate, parent, false)
        return MoneyHolder(view, context, list, user)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MoneyAdapter.MoneyHolder, position: Int) {
        holder.productName.setText(list.get(position)!!.name)
        holder.productCompany.setText(list.get(position)!!.seller)
        holder.productBrand.setText(list.get(position)!!.price.toString())
        val type = list.get(position)!!.productType
        val image = service.getImageByType(type, list[position]!!.productId)
        holder.productImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))
        //holder.productImage.setImageBitmap(list.get(position).Img)
    }

    class MoneyHolder(itemView: View, context: Context, list:List<Product?>, user : User) : RecyclerView.ViewHolder(itemView){
        var productImage : ImageView = itemView.findViewById(R.id.imageViewProductImage)
        var productName : TextView = itemView.findViewById(R.id.textViewProductName)
        var productCompany : TextView = itemView.findViewById(R.id.textViewProductCompany)
        var productBrand : TextView = itemView.findViewById(R.id.textViewProductBrand)

        init{
            itemView.setOnClickListener {
                val inflater = LayoutInflater.from(context)
                var popupView = inflater.inflate(R.layout.popup_price, null)

                val cancelButton = popupView.findViewById<TextView>(R.id.buttonCancel)
                val confirmButton = popupView.findViewById<TextView>(R.id.buttonAdd)
                val price = popupView.findViewById<TextView>(R.id.editTextPrice)

                cancelButton.setOnClickListener {
                    popupView.visibility = View.GONE
                }
                confirmButton.setOnClickListener(){
                    val intent = Intent(context, CatalogActivity::class.java)
                    intent.putExtra("product", list.get(adapterPosition))
                    intent.putExtra("user", user)
                    intent.putExtra("price", price.text.toString())
                    //Crear el producto con httpcalls
                    context.startActivity(intent)
                }
                popupView = PopupWindow(LayoutInflater.from(context).inflate(R.layout.popup_price, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false))
            }
        }
    }
}