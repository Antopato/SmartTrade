package com.example.smarttrade.strategy

import android.content.Context
import com.example.smarttrade.classes.Address
import com.example.smarttrade.classes.User


interface Strategy {
    fun showPopup(context: Context, user: User, address: Address)
}