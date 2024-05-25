package com.example.smarttrade.classes

import java.io.Serializable

class Order(
    var order_id: Int,
    var client: String,
    val order_date: String,
    var deadline: String,
    var state: String
) : Serializable