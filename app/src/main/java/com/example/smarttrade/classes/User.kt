package com.example.smarttrade.classes

import java.io.Serializable

open class User (
    var name: String,
    var password: String,
    var email: String,
    var type: String
) : Serializable
