package com.example.smarttrade.classes.typeofusers

import com.example.smarttrade.classes.User

class Administrator(
    name: String,
    password: String,
    email: String,
    type : String,
    var acces_key: Int,
    var token: Int): User(name, password, email, type) {
}