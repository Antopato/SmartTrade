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
    var state: OrderState = OrderInPreparation()
) : Serializable{
    fun nextState() {
        state.nextState(this)
    }
    fun getState(): String {
        return state.getState()
    }
}