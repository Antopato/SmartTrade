package com.example.smarttrade.classes.food

import com.example.smarttrade.classes.Product

class Fish(
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
    var fishId: Int,
    var fishingMethod: String,
    var calories: Int,
    var expiringDate: String,
    var quantity: Int,
    var unit: String
): Product(productId, name, price, description, production, additionalInfo, image, seller, stock, certificationId, brand, materials) {
}