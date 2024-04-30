package com.example.smarttrade.classes.electronic

import com.example.smarttrade.classes.Product

class Computer (
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
    var operatingSystem: String,
    var storageType: String,
    var ram: Int,
    var guarrantee: Int
    ): Product(productId, name, description, production, additionalInfo, image,certificationId, brand, materials, productType)