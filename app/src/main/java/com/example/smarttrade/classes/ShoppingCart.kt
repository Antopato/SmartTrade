package com.example.smarttrade.classes

data class ShoppingCart(
    var cartId : Int,
    var userId : String,
    var prodId : Int,
    var quantity : Int,
    var price : Int
)
