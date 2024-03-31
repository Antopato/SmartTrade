package com.example.smarttrade

import android.content.Context
import android.graphics.BitmapFactory
import com.example.smarttrade.classes.Product
import android.graphics.Bitmap
import java.io.FileInputStream


class BusinessLogic {
    fun logIn(user:String, pass:String): Boolean {
        if(user!=""){
            if(pass!=""){
                //Send(user,pass)
                return true
            }else{
                throw(Exception("The password section is void"))
            }

        }else{
            throw(Exception("The user section is void"))
        }
    }

    fun registerCompany(name:String, surname:String, email:String, pass:String,
                        holder:String, iban:String, date:String) {

    }

    fun getProduct(context: Context) : List<Product>{
        var bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lavadora)

        val prod1 = Product("Lavadora", "Lava que flipas", bitmap)
        val prod2 = Product("Lavadora", "Lava que flipas la segunda vez tb", bitmap)
        val prod3 = Product("Lavadora", "Lava que flipas la segunda vez tb1", bitmap)
        val prod4 = Product("Lavadora", "Lava que flipas la segunda vez tb2", bitmap)
        val prod5 = Product("Lavadora", "Lava que flipas la segunda vez tb3", bitmap)
        val prod6 = Product("Lavadora", "Lava que flipas la segunda vez tb4", bitmap)
        val prod7 = Product("Lavadora", "Lava que flipas la segunda vez tb5", bitmap)
        val prod8 = Product("Lavadora", "Lava que flipas la segunda vez tb6", bitmap)

        val list = mutableListOf<Product>(prod1, prod2, prod3, prod4, prod5, prod6,prod7,prod8)

        return list

    }
}