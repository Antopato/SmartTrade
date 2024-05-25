package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

class OrderDelivered: OrderState{
    override fun nextState(order: Order) {
    }

    override fun getState(): String {
        return "Order delivered"
    }
}