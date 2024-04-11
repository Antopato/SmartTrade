package com.example.smarttrade.classes.fashion

import com.example.smarttrade.classes.Product

class FashionBot(
    productId: Int,
    name: String,
    price: Double,
    description: String,
    production: String,
    additionalInfo: String,
    image: String,
    seller: String,
    stock: Int,
    certificationId: Int,
    materials: String,
    brand: String,
    var botId: Int,
    var botType: String,
    var size : Int

): Product(productId, name, price, description, production, additionalInfo, image, seller, stock, certificationId, brand, materials)