package com.example.smarttrade.classes.electronic

import com.example.smarttrade.classes.Product

class Computer (
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
    var computerId: Int,
    var operatingSystem: String,
    var storageType: String,
    var ram: Int,
    var guarantee: Int
    ): Product(productId, name, price, description, production, additionalInfo, image, seller, stock, certificationId, brand, materials)