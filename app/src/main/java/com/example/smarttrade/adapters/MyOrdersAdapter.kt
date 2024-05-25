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

class MyOrdersAdapter(var context: Context, var list: List<Order?>) : RecyclerView.Adapter<MyOrdersAdapter.OrdersHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersHolder {
        val inflater : LayoutInflater  = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.my_orders_row, parent, false)
        return OrdersHolder(view, context, list)
    }


    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MyOrdersAdapter.OrdersHolder, position: Int) {
        holder.orderID.setText("#" + list.get(position)!!.order_id)
        holder.orderState.setText(list.get(position)!!.state)
    }

    class OrdersHolder(itemView: View, context: Context, list:List<Order?>) : RecyclerView.ViewHolder(itemView){
        val service = BusinessLogic()
        val orderID = itemView.findViewById<TextView>(R.id.textViewOrder)
        val orderState = itemView.findViewById<TextView>(R.id.textViewState)

        init{
            itemView.setOnClickListener {
                val intent = Intent(context, OrderClickedActivity::class.java)
                intent.putExtra("order", list.get(adapterPosition))
                context.startActivity(intent)
            }
        }
    }
}