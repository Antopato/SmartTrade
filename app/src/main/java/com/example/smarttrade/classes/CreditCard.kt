package com.example.smarttrade.classes

import java.io.Serializable


class CreditCard(var cardNumber : String,
                 var CVV : Int,
                 var owner : String,
                 var expirationDate : String): Serializable