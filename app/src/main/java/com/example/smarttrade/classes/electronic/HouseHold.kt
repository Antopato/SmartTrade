package com.example.smarttrade.classes.electronic

import com.example.smarttrade.classes.Product

class HouseHold(
    productId: Int,
    name: String,
    description: String,
    production: String,
    additionalInfo: String,
    image: String,
    certificationId: Int,
    materials: String,
    brand: String,
    productType : String,
    var powerConsumption: Int,
    var noiseLevel: Double,
    var guarantee: Int): Product(productId, name,description, production, additionalInfo, image, certificationId, brand, materials, productType)