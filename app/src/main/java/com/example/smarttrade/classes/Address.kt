package com.example.smarttrade.classes

import java.io.Serializable

class Address(
    var addresId: Int,
    var city: String,
    var postalCode: Int,
    var province: String,
    var street: String,
    var addresOf: String
): Serializable