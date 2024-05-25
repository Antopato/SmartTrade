package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

class OrderWaiting: OrderState{
    override fun nextState(order: Order) {
        order.state = OrderOut()
    }

    override fun getState(): String {
        return "Waiting for pickup at warehouse"
    }
}