package com.example.smarttrade

import com.example.smarttrade.classes.Address
import com.example.smarttrade.classes.Administrator
import com.example.smarttrade.classes.Certificate
import com.example.smarttrade.classes.Costumer
import com.example.smarttrade.classes.Merchant
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Url

interface APIServer {
    @GET
    fun getAllUsers(@Url url: String): Response<List<User>>
    @PUT
    fun createCostumer(@Url url:String,@Body costumer : Costumer)
    @PUT
    fun createMerchant(@Url url:String,@Body merchant: Merchant)
    @GET
    fun getAdministrators(@Url url: String): Response<List<Administrator>>
    @GET
    fun getCertificates(@Url url: String): Response<List<Certificate>>
    @PUT
    fun createCertificate(@Url url:String,@Body certificate: Certificate)
    @PUT
    fun createProduct(@Url url:String,@Body product: Product)
    @GET
    fun getAddress(@Url url: String): Response<Address>
    @PUT
    fun createAddress(@Url url:String,@Body address: Address)

    @GET
    fun getAllProducts() : Response<List<Product>>

    @GET
    suspend fun getUserById(@Url url:String) : Response<User>


}