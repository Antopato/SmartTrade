package com.example.smarttrade.classes.fashion

import com.example.smarttrade.classes.Product

class FootWear(
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
    var footwearId: Int,
    var footwearType: String,
    var size: Int
) : Product(productId, name, price, description, production, additionalInfo, image, seller, stock, certificationId, brand, materials) {
}