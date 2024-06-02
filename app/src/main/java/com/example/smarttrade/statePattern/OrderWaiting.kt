package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

class OrderWaiting: OrderState{
    override fun inPreparationState(order: Order) {
        println("Cannot move back to preparation state")
    }

    override fun waitingState(order: Order) {
        println("Already in waiting state")
    }

    override fun outfordeliveryState(order: Order) {
        order.state = OrderOut()
        order.stateString = "Order out for delivery"
        println("Order moved to out for delivery state")
    }

    override fun deliveredState(order: Order) {
        println("Cannot deliver directly from waiting")
    }

    override fun getState(): String {
        return "Waiting for pickup at warehouse"
    }
}