package com.example.smarttrade

import android.content.Context
import android.graphics.BitmapFactory
import com.example.smarttrade.classes.Product
import android.graphics.Bitmap
import com.example.smarttrade.classes.Certificate
import com.example.smarttrade.classes.Price
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
    fun getPrice():List<Price>{
        val price1 = Price("Siemens", 200, "Lavadora1")
        val price2 = Price("Siemens", 400, "Lavadora4")
        val price3 = Price("Siemens", 1200, "Lavadora3")
        val price4 = Price("Siemens", 22200, "Lavadora2")

        val list = mutableListOf<Price>(price1,price2,price3,price4)
        return list

    }

    fun getCeritificate(context:Context):List<Certificate>{
        var bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lavadora)
        val cert1 = Certificate("Lavadora1", "Siemens1", "Siemens1", bitmap)
        val cert2 = Certificate("Lavadora2", "Siemens2", "Siemens2", bitmap)
        val cert3 = Certificate("Lavadora3", "Siemens3", "Siemens3", bitmap)
        val cert4 = Certificate("Lavadora4", "Siemens4", "Siemens4", bitmap)

        val list = mutableListOf<Certificate>(cert1,cert2,cert3,cert4)
        return list
    }
}