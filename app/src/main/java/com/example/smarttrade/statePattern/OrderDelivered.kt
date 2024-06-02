package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

class OrderDelivered: OrderState{
    override fun inPreparationState(order: Order) {
        println("Cannot move to preparation state from delivered")
    }

    override fun waitingState(order: Order) {
        println("Cannot move to waiting state from delivered")
    }

    override fun outfordeliveryState(order: Order) {
        println("Cannot move to out for delivery state from delivered")
    }

    override fun deliveredState(order: Order) {
        println("Order is already delivered")
    }

    override fun getState(): String {
        return "Order delivered"
    }
}