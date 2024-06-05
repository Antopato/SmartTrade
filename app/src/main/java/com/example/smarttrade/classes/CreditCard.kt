package com.example.smarttrade.classes

import java.io.Serializable


class CreditCard(var card_number : String,
                 var cvv : Int,
                 var credit_card_owner : String,
                 var expiring_date : String): Serializable