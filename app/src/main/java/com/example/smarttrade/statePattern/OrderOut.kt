package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

class OrderOut: OrderState{
    override fun nextState(order: Order) {
        order.state = OrderDelivered()
    }

    override fun getState(): String {
        return "Order out for delivery"
    }
}