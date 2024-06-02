package com.example.smarttrade.statePattern

import com.example.smarttrade.classes.Order

interface OrderState {
    fun inPreparationState(order: Order)
    fun waitingState(order: Order)
    fun outfordeliveryState(order: Order)
    fun deliveredState(order: Order)
    fun getState(): String
}