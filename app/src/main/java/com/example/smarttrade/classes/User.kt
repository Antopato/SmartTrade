package com.example.smarttrade.classes

import java.util.Date

data class User(val name: String, val password : String, val email : String, val birthdate : Date, val balance: Int, val type : String )
