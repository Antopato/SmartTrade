package com.example.smarttrade

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import com.example.smarttrade.classes.Certificate
import com.example.smarttrade.classes.Price
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BusinessLogic {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https:localhost:8888/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun logIn(mail:String, pass:String, activity : Activity): Boolean {

        if(mail!=""){
            if(pass!=""){
                //Send(user,pass)
                CoroutineScope(Dispatchers.IO).launch{
                    val call: Response<User> = getRetrofit().create(APIServer::class.java).getUserById("clients/client/"+mail)
                    println("Se ha realizado la petición")
                    activity.runOnUiThread() {
                        if (call.isSuccessful) {
                            if(call.body() != null){
                                if(call.body()!!.password == pass){
                                    activity.
                                }
                            }else{
                                throw (Exception("Email not found"))
                            }

                        } else {
                            throw (Exception(call.errorBody().toString()))
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

    fun getProduct(context: Context) : List<Product> {
        /*var list = MutableStateFlow<List<Product>?>(null)

        CoroutineScope(Dispatchers.IO).launch {
            val call: Deferred<Response<List<Product>>> = async{ getRetrofit().create(APIServer::class.java).getAllProducts()}

            list.emit(call.await().body())
        }*/
        var bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lavadora)

        var prod1 = Product("Lavadora","Lava que flipas",bitmap)
        var list = listOf<Product>(prod1)
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


