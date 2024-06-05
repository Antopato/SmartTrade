package com.example.smarttrade.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.R
import com.example.smarttrade.classes.Order

class MerchantOrdersAdapter(var context: Context, var list: List<Order?>) : RecyclerView.Adapter<MerchantOrdersAdapter.MerchantOrdersHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchantOrdersHolder {
        val inflater : LayoutInflater  = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.merchant_orders_row, parent, false)
        return MerchantOrdersHolder(view, context, list)
    }


    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MerchantOrdersAdapter.MerchantOrdersHolder, position: Int) {
        val order = list[position]!!
        holder.orderID.setText("#" + list.get(position)!!.order_id)
        holder.orderState.text = order.getState()

        if (order.getState() == "Order delivered") {
            holder.button.visibility = View.GONE
        }

        holder.button.setOnClickListener {
            when (order.getState()) {
                "Order in preparation" -> order.waitingState()
                "Waiting for pickup at warehouse" -> order.outForDeliveryState()
                "Order out for delivery" -> order.deliveredState()
            }

            holder.orderState.text = order.getState()

            notifyItemChanged(position)
            service.notifyState(order.order_id, order.getState())
        }
    }

    class MerchantOrdersHolder(itemView: View, context: Context, list:List<Order?>) : RecyclerView.ViewHolder(itemView){
        val orderID = itemView.findViewById<TextView>(R.id.textViewOrder)
        val orderState = itemView.findViewById<TextView>(R.id.textViewState)
        val button = itemView.findViewById<Button>(R.id.btnNext)
    }
}