package com.example.smarttrade.classes.electronic

import com.example.smarttrade.classes.Product

class HouseHold(
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
    var powerConsumption: Int,
    var noiseLevel: Double,
    var guarantee: Int): Product(productId, name, price, description, production, additionalInfo, image, seller, stock, certificationId, brand, materials)