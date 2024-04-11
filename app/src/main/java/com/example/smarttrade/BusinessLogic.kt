package com.example.smarttrade

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.ProductsAdapter
import com.example.smarttrade.classes.Certificate
import com.example.smarttrade.classes.Price
import com.example.smarttrade.classes.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
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
                    var user = call.getUserById(mail).await()
                    println(user.toString())
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

    fun getProduct(context: Context)  {

        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<Product>> = getRetrofit().create(APIServer::class.java).getAllProducts("products/")
            if(call.isSuccessful){
                activity.runOnUiThread(){
                    val list = call.body()
                    if(list!=null) {

                        val recycler = activity.findViewById<RecyclerView>(R.id.recyclerView)

                        val adapter = ProductsAdapter(activity.applicationContext, list)

                        recycler.adapter=adapter


                    }
                }
            }else{
                println(call.errorBody())

            }

        }

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


        return list
    }
}


