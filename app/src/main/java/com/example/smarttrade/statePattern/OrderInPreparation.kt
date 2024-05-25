package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

class OrderInPreparation: OrderState{
    override fun nextState(order: Order) {
        order.state = OrderWaiting()
    }

    override fun getState(): String {
        return "Order in preparation"
    }
}