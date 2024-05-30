package com.example.smarttrade.strategy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import com.example.smarttrade.MyOrdersActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Address
import com.example.smarttrade.classes.User

class CheckoutStrategy: Strategy {
    override fun showPopup(context: Context, user: User, address: Address) {
        val inflater = LayoutInflater.from(context)
        val popupView = inflater.inflate(R.layout.checkout_popup, null)

        val width = 900
        val height = 450

        val popupWindow = PopupWindow(popupView,
            width,
            height,
            true)

        val yesButton = popupView.findViewById<Button>(R.id.buttonAdd)
        val noButton = popupView.findViewById<Button>(R.id.buttonCancel)

        yesButton.setOnClickListener {
            val intent = Intent(context, MyOrdersActivity::class.java)
            intent.putExtra("user", user)
            intent.putExtra("address", address)
            context.startActivity(intent)
            popupWindow.dismiss()
        }
        noButton.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.showAtLocation((context as Activity).window.decorView, Gravity.CENTER, 0, 0)
    }
}