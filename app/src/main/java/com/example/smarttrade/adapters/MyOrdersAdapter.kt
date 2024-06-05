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
import androidx.constraintlayout.widget.ConstraintLayout
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
        val order = list[position]!!
        println("list en el viewHolder: "+list)
        holder.orderID.setText("#" + list.get(position)!!.order_id)
        holder.orderState.text = order.getState()
        holder.row.setOnClickListener {
            println("piche")
            val intent = Intent(context, OrderClickedActivity::class.java)
            intent.putExtra("order_id", order.order_id)
            intent.putExtra("client", order.client)
            intent.putExtra("state", order.getState())
            context.startActivity(intent)
        }
    }

    class OrdersHolder(itemView: View, context: Context, list:List<Order?>) : RecyclerView.ViewHolder(itemView){
        val service = BusinessLogic()
        val orderID = itemView.findViewById<TextView>(R.id.textViewOrder)
        val orderState = itemView.findViewById<TextView>(R.id.textViewState)
        val row = itemView.findViewById<ConstraintLayout>(R.id.layoutOrder)
    }
}