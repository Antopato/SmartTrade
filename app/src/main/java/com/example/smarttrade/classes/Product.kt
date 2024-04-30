package com.example.smarttrade.classes
import java.io.Serializable

open class Product(
    var productId : Int,
    var name: String,
    var description: String,
    var production: String,
    var additionalInfo: String,
    var image: String,
    var certificationId: Int,
    var brand: String,
    var materials: String,
    var productType : String
) : Serializable



