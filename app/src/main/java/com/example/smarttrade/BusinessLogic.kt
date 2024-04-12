package com.example.smarttrade

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import com.example.smarttrade.classes.Price
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BusinessLogic(private val activity: Activity) {

    var call = HTTPcalls(activity)
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https:localhost:8888/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun logIn(mail:String, pass:String): Boolean {

        if(mail!=""){
            if(pass!=""){
                runBlocking {
                    var user : User? = call.getUserById(mail).await()
                    println(user.toString())
                    if(user == null){
                        throw(Exception("The mail is incorrect"))
                    }else{
                        if(pass!=user.password){
                            throw(Exception("Incorrect password"))
                        }
                    }
                }

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

    fun getProducts(context: Context) : List<Product?> {
        var list : List<Product?>
        runBlocking {
            list = call.getAllProducts().await()

        }
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

    fun getCeritificate(context:Context){

        var bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lavadora)

        //var list = List<Certificate>
        //return list
    }
}


