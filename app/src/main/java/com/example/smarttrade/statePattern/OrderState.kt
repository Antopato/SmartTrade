package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

interface OrderState {
    fun nextState(order: Order)
    fun getState(): String
}