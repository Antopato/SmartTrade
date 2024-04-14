package com.example.smarttrade.classes.typeofusers

import com.example.smarttrade.classes.User
import java.io.Serializable


class Costumer(
    name: String,
    password: String,
    email: String,
    type : String,
    var birthdateString: String,
    var balance: Int
): User(name, password, email,type), Serializable {
}