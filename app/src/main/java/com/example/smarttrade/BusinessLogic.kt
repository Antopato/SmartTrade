package com.example.smarttrade

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import com.example.smarttrade.classes.Certification
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.classes.User
import com.example.smarttrade.classes.electronic.Computer
import com.example.smarttrade.classes.electronic.HouseHold
import com.example.smarttrade.classes.electronic.SmartPhone
import com.example.smarttrade.classes.fashion.FashionBot
import com.example.smarttrade.classes.fashion.FashionTop
import com.example.smarttrade.classes.fashion.FootWear
import com.example.smarttrade.classes.food.Drink
import com.example.smarttrade.classes.food.Fish
import com.example.smarttrade.classes.food.Fruit
import com.example.smarttrade.classes.food.Meat
import com.example.smarttrade.classes.food.Vegetable
import kotlinx.coroutines.Deferred
import com.example.smarttrade.classes.typeofusers.Costumer
import com.example.smarttrade.classes.typeofusers.Merchant
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import java.io.File


class BusinessLogic() {

    var call = HTTPcalls()

    fun logIn(mail:String, pass:String): User? {

        if(mail!=""){
            if(pass!=""){
                var user : User?
                runBlocking {
                    user = call.getUserById(mail).await()
                    println(user.toString())
                    if(user == null){
                        throw(Exception("The mail is incorrect"))
                    }else{
                        if(pass!= user!!.password){
                            throw(Exception("Incorrect password"))
                        }
                    }
                }

                return user

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

    fun getProducts() : List<Product?> {
        var list : List<Product?>
        runBlocking {
            list = call.getAllProducts().await()
        }
        return list

    }



    fun getCategoryProducts(type:String):List<Product>{
        var list : List<Product>
        runBlocking{
            when (type) {
                "COMPUTER" -> {
                    list = call.getCategoryProducts("electronics/computer").await()
                }

                "HOUSEHOLD" -> {
                    list = call.getCategoryProducts("electronics/household").await()
                }

                "PHONE" -> {
                    list = call.getCategoryProducts("electronics/smartphone").await()
                }

                "MEAT" -> {
                    list = call.getCategoryProducts("food/meat").await()
                }

                "FRUIT" -> {
                    list = call.getCategoryProducts("food/fruit").await()
                }

                "DRINK" -> {
                    list = call.getCategoryProducts("food/drink").await()
                }

                "VEGETABLE" -> {
                    list = call.getCategoryProducts("food/vegetables").await()
                }

                "FISH" -> {
                    list = call.getCategoryProducts("food/fish").await()
                }

                "FOOTWEAR" -> {
                    list = call.getCategoryProducts("fashion/footwear" ).await()
                }

                "FASHIONBOTTOM" -> {
                    list = call.getCategoryProducts("fashion/fashionbot" ).await()
                }

                "FASHIONTOP" -> {
                    list = call.getCategoryProducts("fashion/fashiontop").await()
                }

                else -> {
                    println("no he econtrado el tipo")
                    list = mutableListOf<Product>()
                }
            }
        }
        return list

    }

    fun getCeritificate(context:Context){

        var bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lavadora)

        //var list = List<Certificate>
        //return list
    }

    fun createCostumer(costumer: Costumer) : User? {
        val user : User?
        runBlocking {
            user = call.createCostumer(costumer).await()
        }
        return user
    }
    fun createMerchant(merchant: Merchant): User? {
        val user : User?
        runBlocking {
            user = call.createMerchant(merchant).await()
        }
        return user
    }
    fun getUncertifiedCertificates(): List<Product?> {
        var products : List<Product?>
        runBlocking {
            products = call.getUncertifiedCertificates().await()
        }
        return products
    }

    fun getCertificates(user: User): List<Product?> {
        var products : List<Product?>
        runBlocking {
            products = call.getCertificates(user).await()
        }
        return products
    }

    fun getImageByType(type:String, id:Int):ByteArray
    {
         var image:ByteArray
        runBlocking {
            println("buscadon imagenes de cualqueri tipo")
            when (type) {
                "COMPUTER" -> {
                    image = call.getComputerImage("electronics/computer/image/" + id).await()
                }

                "HOUSEHOLD" -> {
                    image = call.getComputerImage("electronics/household/image/" + id).await()
                }

                "PHONE" -> {
                    image = call.getComputerImage("electronics/smartphone/image/" + id).await()
                }

                "MEAT" -> {
                    image = call.getComputerImage("food/meat/image/" + id).await()
                }

                "FRUIT" -> {
                    image = call.getComputerImage("food/fruit/image/" + id).await()
                }

                "DRINK" -> {
                    image = call.getComputerImage("food/drink/image/" + id).await()
                }

                "VEGETABLE" -> {
                    image = call.getComputerImage("food/vegetables/image/" + id).await()
                }

                "FISH" -> {
                    image = call.getComputerImage("food/fish/image/" + id).await()
                }

                "FOOTWEAR" -> {
                    image = call.getComputerImage("fashion/footwear/image/" + id).await()
                }

                "FASHIONBOTTOM" -> {
                    image = call.getComputerImage("fashion/fashionbot/image/" + id).await()
                }

                "FASHIONTOP" -> {
                    image = call.getComputerImage("fashion/fashiontop/image/" + id).await()
                }
                else -> {
                    println("no he econtrado el tipo")
                    image = ByteArray(0)
                }
            }
        }
        println(image.toString())
        return image
    }

    fun validateCertificate(product:Product?, user: User){
        if (product != null) {
            call.updateCertification(product.certificationId,true,user.email)
        }
    }

    fun declineCertificate(product:Product?, user: User){
        if (product != null) {
            call.updateCertification(product.certificationId,false,user.email)
        }
    }

    fun createComputer(product: Computer, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createComputer(
                byteArray,
                product.brand,
                product.description,
                product.guarrantee,
                product.materials,
                product.name,
                product.operatingSystem,
                seller.seller_email,
                seller.price,
                product.production,
                product.ram,
                seller.stock,
                product.storageType
                ).await()
        }
    }

    fun createSmartphone(product: SmartPhone, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createSmartphone(
                byteArray,
                product.brand,
                product.description,
                product.display,
                product.guarrantee,
                product.materials,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.processor,
                seller.stock,
                product.size
            ).await()
        }
    }

    fun createHousehold(product: HouseHold, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createHousehold(
                byteArray,
                product.brand,
                product.description,
                product.guarrantee,
                product.materials,
                product.name,
                product.noiseLevel,
                seller.seller_email,
                seller.price,
                product.production,
                product.powerConsumption,
                seller.stock
            ).await()
        }
    }

    fun createFashionTop(product: FashionTop, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createFashionTop(
                byteArray,
                product.brand,
                product.description,
                product.materials,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.size,
                seller.stock,
                product.topType
            ).await()
        }
    }

    fun createFashionBottom(product: FashionBot, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createFashionBottom(
                byteArray,
                product.brand,
                product.description,
                product.materials,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.size,
                seller.stock,
                product.botType
            ).await()
        }
    }

    fun createFootWear(product: FootWear, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createFootWear(
                byteArray,
                product.brand,
                product.description,
                product.materials,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.size,
                seller.stock,
                product.footwearType
            ).await()
        }
    }

    fun createDrink(product: Drink, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createDrink(
                byteArray,
                product.alcohol,
                product.calories,
                product.expiringDate,
                product.drinkType,
                product.description,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.quantity,
                seller.stock,
                product.unit
            ).await()
        }
    }

    fun createFish(product: Fish, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createFish(
                byteArray,
                product.calories,
                product.expiringDate,
                product.fishingMethod,
                product.description,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.quantity,
                seller.stock,
                product.unit
            ).await()
        }
    }

    fun createFruit(product: Fruit, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createFruit(
                byteArray,
                product.calories,
                product.expiringDate,
                product.flavor,
                product.description,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.quantity,
                seller.stock,
                product.unit
            ).await()
        }
    }

    fun createMeat(product: Meat, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createMeat(
                byteArray,
                product.calories,
                product.expiringDate,
                product.origin,
                product.description,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.quantity,
                seller.stock,
                product.unit
            ).await()
        }
    }

    fun createVegetable(product: Vegetable, seller: Sell, byteArray: ByteArray){
        runBlocking {
            call.createVegetable(
                byteArray,
                product.calories,
                product.expiringDate,
                product.origin,
                product.description,
                product.name,
                seller.seller_email,
                seller.price,
                product.production,
                product.quantity,
                product.season,
                seller.stock,
                product.unit
            ).await()
        }
    }

    fun getMerchantProductsById(id: String): List<Product?>{
        var products : List<Product?>
        runBlocking {
            products = call.getMerchantProductsById(id).await()
        }
        return products
    }

    fun getMerchantProductsPending(id: String): List<Product?>{
        var products : List<Product?>
        runBlocking {
            products = call.getMerchantProductsPending(id).await()
        }
        return products
    }
    fun getMerchantProductsDenied(id: String): List<Product?>{
        var products : List<Product?>
        runBlocking {
            products = call.getMerchantProductsDenied(id).await()
        }
        return products
    }

    fun getShoppingCar(id : String) : MutableList<ShoppingCart>{
        var list = mutableListOf<ShoppingCart>()
        runBlocking {
            list.addAll(call.getCartById(id).await())
        }
        return list
    }

    fun deleteProdFromCart(id : Int, email : String){
        runBlocking{
            call.deleteProdFromCart(id,email)
        }

    }
    fun addProductToCar(sell : Sell, email : String){
        runBlocking {
            call.addProdToCart(sell, email).await()
        }
    }

    fun getWhislist(userId : String) : MutableList<Product>{
        var list = mutableListOf<Product>()
        runBlocking {
            list.addAll(call.getWhisList(userId).await())
        }
        return list
    }

    fun getForLaterList(userId : String) : MutableList<Product>{
        var list = mutableListOf<Product>()
        runBlocking {
            list.addAll(call.getForLaterList(userId).await())
        }
        return list
    }

    fun addToLaterList(prodId : Int , userId : String) {
        runBlocking{
            call.addProductToForLater(userId,prodId).await()
        }
    }
    fun addToWhislist(prodId : Int , userId : String){
        runBlocking{
            call.addProducToWhislist(userId,prodId).await()
        }
    }
    fun deleteProdFromWhislist(prodId : Int, userId : String){
        runBlocking{
            call.deleteProdFromWhislist(prodId, userId).await()
        }
    }
    fun deleteProdFromLater(prodId : Int, userId : String){
        runBlocking{
            call.deleteProdFromForLater(prodId,userId).await()
        }
    }

    fun getSeller(productId : Int) : List<Sell>{
        val list = mutableListOf<Sell>()
        runBlocking{
            list.addAll(call.getSeller(productId).await())
        }
        return list
    }

    fun getProductById(id : Int) : Product{
        val product : Product?
        runBlocking {
            product = call.getProductById(id).await()
        }
        if(product!=null) {
            return product
        }else{
            throw Exception("Product not Found")
        }
    }

    fun copyProduct(seller: Sell){
        runBlocking { call.copyProduct(seller).await() }
    }

    fun getAvgPrice(id : Int) : Double{
        val price : Double
        runBlocking {
            price = call.getAvgPrice(id).await()
        }
        return price
    }

    fun deleteAllShoppingCart(email : String){
        runBlocking{ call.deleteAllShopping(email).await() }
    }
    fun deleteAllForLater(email : String){
        runBlocking{ call.deleteAllForLater(email).await() }
    }
    fun deleteAllWhislist(email : String){
        runBlocking { call.deleteAllWhislist(email).await() }
    }

}


