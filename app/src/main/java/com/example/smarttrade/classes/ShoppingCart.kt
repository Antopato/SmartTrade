package com.example.smarttrade.classes

import android.os.Parcelable
import java.io.Serializable

data class ShoppingCart(
    var shoppingCart_id : Int,
    var shopping_cart_owner : String,
    var product_id : Int,
    var quantity : Int,
    var price : Int
) : Serializable
