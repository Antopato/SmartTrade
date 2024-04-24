package com.example.smarttrade

import android.content.Context
import android.graphics.BitmapFactory
import com.example.smarttrade.classes.Certification
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
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

    fun createComputer(computer: Computer, email: String){
        runBlocking {
            call.createComputer(computer, email).await()
        }
    }

    fun createPhone(phone: SmartPhone, email: String){
        runBlocking {
            call.createPhone(phone, email).await()
        }
    }

    fun createHousehold(household: HouseHold, email: String){
        runBlocking {
            call.createHousehold(household, email).await()
        }
    }

    fun createFootwear(footwear: FootWear, email: String){
        runBlocking {
            call.createFootwear(footwear, email).await()
        }
    }

    fun createFashionTop(fashionTop: FashionTop, email: String){
        runBlocking {
            call.createFashiontop(fashionTop, email).await()
        }
    }

    fun createFashioBot(fashionBot: FashionBot, email: String){
        runBlocking {
            call.createFashionbot(fashionBot, email).await()
        }
    }

    fun createFruit(fruit: Fruit, email: String){
        runBlocking {
            call.createFruit(fruit, email).await()
        }
    }

    fun createFish(fish: Fish, email: String){
        runBlocking {
            call.createFish(fish, email).await()
        }
    }

    fun createDrink(drink: Drink, email: String){
        runBlocking {
            call.createDrink(drink, email).await()
        }
    }

    fun createMeat(meat: Meat, email: String){
        runBlocking {
            call.createMeat(meat, email).await()
        }
    }

    fun createVegetable(vegetable: Vegetable, email: String){
        runBlocking {
            call.createVegetables(vegetable, email).await()
        }
    }

    fun getMerchantProductsById(id: String): List<Product?>{
        var products : List<Product?>
        runBlocking {
            products = call.getMerchantProductsById(id).await()
        }
        return products
    }

    fun getShoppingCar() : MutableList<Sell>{
        val list = mutableListOf<Sell>()
        val sell1 = Sell("lavadora","pele","100","1")
        val sell2 = Sell("lavadora", "pel","150","1")
        list.add(sell1)
        list.add(sell2)
        return list
    }

}


