package com.example.smarttrade.classes.typeofusers

import com.example.smarttrade.classes.User
import java.io.Serializable
import java.util.Date

class Merchant(
    name: String,
    password: String,
    email: String,
    type: String,
    var incorporation_date: String,
    var enterprise_name: String
): User(name, password, email, type), Serializable
