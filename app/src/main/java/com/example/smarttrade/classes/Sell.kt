package com.example.smarttrade.classes

data class Sell(
    var id_selled_by : Int,
    var id_product : Int,
    var seller_email : String,
    var stock : Int,
    var price : Double
)
