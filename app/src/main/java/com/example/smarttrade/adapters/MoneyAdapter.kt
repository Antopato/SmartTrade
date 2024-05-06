package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.User
import com.example.smarttrade.classes.typeofusers.Merchant
import java.io.ByteArrayOutputStream

class MoneyAdapter(var context: Context, var list: List<Product?>, var user: User) : RecyclerView.Adapter<MoneyAdapter.MoneyHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyHolder {
        val inflater : LayoutInflater  = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.recycler_row_certificate, parent, false)
        println(user.email)
        return MoneyHolder(view, context, list, user)
    }


    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MoneyAdapter.MoneyHolder, position: Int) {
        holder.productName.setText(list.get(position)!!.name)
        holder.productCompany.setText(list.get(position)!!.description)
        holder.productBrand.setText(list.get(position)!!.brand)
        val type = list.get(position)!!.productType
        val image = service.getImageByType(type, list[position]!!.productId)
        holder.productImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))
        //holder.productImage.setImageBitmap(list.get(position).Img)
    }

    class MoneyHolder(itemView: View, context: Context, list:List<Product?>, user : User) : RecyclerView.ViewHolder(itemView){
        val service = BusinessLogic()
        var productImage : ImageView = itemView.findViewById(R.id.imageViewProductImage)
        var productName : TextView = itemView.findViewById(R.id.textViewProductName)
        var productCompany : TextView = itemView.findViewById(R.id.textViewProductCompany)
        var productBrand : TextView = itemView.findViewById(R.id.textViewProductBrand)

        init{
            itemView.setOnClickListener {
                val widthInPixels = 920
                val heightInPixels = 570
                val popupView = LayoutInflater.from(itemView.context).inflate(R.layout.popup_price, null)
                val popupWindow = PopupWindow(popupView, widthInPixels, heightInPixels, true)
                val price = popupView.findViewById<EditText>(R.id.editTextPrice)

                val buttonCancel = popupView.findViewById<View>(R.id.buttonCancel)
                buttonCancel.setOnClickListener(){
                    popupWindow.dismiss()
                }
                val buttonAdd = popupView.findViewById<View>(R.id.buttonAdd)
                buttonAdd.setOnClickListener(){
                    popupWindow.dismiss()
                    val intent = Intent(context, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    val sell = Sell(0, list.get(adapterPosition)!!.productId, user.email, 1, price.text.toString().toDouble())
                    service.copyProduct(sell)
                    context.startActivity(intent)
                }
                popupWindow.showAtLocation(itemView, Gravity.CENTER, 0, 0)
            }
        }
    }
}