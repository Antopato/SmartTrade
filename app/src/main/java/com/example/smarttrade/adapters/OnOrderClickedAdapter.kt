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
import com.example.smarttrade.OrderClickedActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Order
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.User
import com.example.smarttrade.classes.typeofusers.Merchant
import java.io.ByteArrayOutputStream

class OnOrderClickedAdapter(var context: Context, var list: List<Product?>, orderState: String) : RecyclerView.Adapter<OnOrderClickedAdapter.OrderProductsHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductsHolder{
        val inflater : LayoutInflater  = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.order_clicked_row, parent, false)
        return OrderProductsHolder(view, context, list, orderState)
    }


    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: OnOrderClickedAdapter.OrderProductsHolder, position: Int) {
        holder.productName.setText(list.get(position)!!.name)
        var product = list.get(position)!!

        val image = service.getImageByType(product.productType, product.productId)
        holder.productImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))

        if(holder.orderState == "Delivered"){
            holder.buttonRate.visibility = View.VISIBLE
        }
    }

    class OrderProductsHolder(itemView: View, context: Context, list:List<Product?>, orderState: String) : RecyclerView.ViewHolder(itemView){
        val service = BusinessLogic()
        val productName = itemView.findViewById<TextView>(R.id.textViewProductName)
        val productImage = itemView.findViewById<ImageView>(R.id.imageViewProductImage)
        val buttonRate = itemView.findViewById<TextView>(R.id.buttonRate)
        val orderState = orderState
    }
}