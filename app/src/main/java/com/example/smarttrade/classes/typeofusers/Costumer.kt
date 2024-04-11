package com.example.smarttrade.classes.typeofusers

import com.example.smarttrade.classes.User

class Costumer(
    name: String,
    password: String,
    email: String,
    type: String,
    var birthdate: String,
    var balance: Int
): User(name, password, email, type) {
}