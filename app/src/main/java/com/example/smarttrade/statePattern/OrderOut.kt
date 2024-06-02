package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

class OrderOut: OrderState{
    override fun inPreparationState(order: Order) {
        println("Cannot move back to preparation state")
    }

    override fun waitingState(order: Order) {
        println("Cannot move back to waiting state")
    }

    override fun outfordeliveryState(order: Order) {
        println("Already out for delivery")
    }

    override fun deliveredState(order: Order) {
        order.state = OrderDelivered()
        order.stateString = "Order delivered"
        println("Order delivered")
    }

    override fun getState(): String {
        return "Order out for delivery"
    }
}