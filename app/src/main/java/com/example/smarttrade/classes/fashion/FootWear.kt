package com.example.smarttrade.classes.fashion

import com.example.smarttrade.classes.Product

class FootWear(
    productId: Int,
    name: String,
    description: String,
    production: String,
    additionalInfo: String,
    image: String,
    certificationId: Int,
    materials: String,
    brand: String,
    productType :String,
    var footwearType: String,
    var size: Int
) : Product(productId, name,description, production, additionalInfo, image, certificationId, brand, materials,productType) {
}