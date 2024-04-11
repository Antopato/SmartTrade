package com.example.smarttrade

import com.example.smarttrade.classes.Address
import com.example.smarttrade.classes.typeofusers.Administrator
import com.example.smarttrade.classes.Certificate
import com.example.smarttrade.classes.typeofusers.Costumer
import com.example.smarttrade.classes.typeofusers.Merchant
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Url

interface APIServer {
    @GET
    suspend fun getAllUsers(@Url url: String): Response<List<User>>
    @PUT
    suspend fun createCostumer(@Url url:String,@Body costumer : Costumer): Call<Costumer>
    @PUT
    suspend fun createMerchant(@Url url:String,@Body merchant: Merchant): Call<Merchant>
    @GET
    suspend fun getAdministrators(@Url url: String): Response<List<Administrator>>
    @GET
    suspend fun getCertificates(@Url url: String): Response<List<Certificate>>
    @PUT
    suspend fun createCertificate(@Url url:String,@Body certificate: Certificate): Call<Certificate>
    @PUT
    suspend fun createProduct(@Url url:String,@Body product: Product): Call<Product>
    @GET
    suspend fun getAddress(@Url url: String): Response<Address>
    @PUT
    suspend fun createAddress(@Url url:String,@Body address: Address): Call<Address>

    @GET
    suspend fun getAllProducts() : Response<List<Product>>

    @GET
    suspend fun getUserById(@Url url:String) : Response<User>


}