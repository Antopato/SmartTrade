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
    fun inPreparationState() {
        state.inPreparationState(this)
    }

    fun waitingState() {
        state.waitingState(this)
    }

    fun outForDeliveryState() {
        state.outfordeliveryState(this)
    }

    fun deliveredState() {
        state.deliveredState(this)
    }
    fun getState(): String {
        return state.getState()
    }
    fun setState(){
        println(this.stateString)
        when(this.stateString){
            "Order in preparation" -> this.state = OrderInPreparation()
            "Waiting for pickup at warehouse" -> this.state = OrderWaiting()
            "Order out for delivery" -> this.state = OrderOut()
            "Order delivered" -> this.state = OrderDelivered()
        }
    }
}