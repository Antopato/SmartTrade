package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

class OrderInPreparation: OrderState{
    override fun inPreparationState(order: Order) {
        println("Already in preparation state")
    }

    override fun waitingState(order: Order) {
        order.state = OrderWaiting()
        order.stateString = "Waiting for pickup at warehouse"
        println("Order moved to waiting state")
    }

    override fun outfordeliveryState(order: Order) {
        println("Cannot move to out for delivery state from preparation")
    }

    override fun deliveredState(order: Order) {
        println("Cannot deliver directly from preparation")
    }

    override fun getState(): String {
        return "Order in preparation"
    }
}