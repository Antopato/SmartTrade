package com.example.smarttrade.classes.food

import com.example.smarttrade.classes.Product

class Drink(
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

    var alcohol: Int,
    var drinkType: String,
    var calories: Int,
    var expiringDate: String,
    var quantity: Int,
    var unit: String
): Product(productId, name,description, production, additionalInfo, image, certificationId, brand, materials,productType) {
}