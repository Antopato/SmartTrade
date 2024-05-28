package com.example.smarttrade.classes

import android.widget.TextView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.statePattern.OrderDelivered
import com.example.smarttrade.statePattern.OrderInPreparation
import com.example.smarttrade.statePattern.OrderOut
import com.example.smarttrade.statePattern.OrderState
import com.example.smarttrade.statePattern.OrderWaiting
import java.io.Serializable

class Order(
    var order_id: Int,
    var client: String,
    val order_date: String,
    var deadline: String,
    var stateString: String
) : Serializable{

    lateinit var state: OrderState
    fun nextState() {
        state.nextState(this)
    }
    fun getState(): String {
        return state.getState()
    }
    fun setState(){
        when(stateString){
            "Order in preparation" -> state = OrderInPreparation()
            "Waiting for pickup at warehouse" -> state = OrderWaiting()
            "Order out for delivery" -> state = OrderOut()
            "Order delivered" -> state = OrderDelivered()
        }

    }
}