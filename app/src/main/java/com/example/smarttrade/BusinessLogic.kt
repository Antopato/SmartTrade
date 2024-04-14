package com.example.smarttrade

import android.content.Context
import android.graphics.BitmapFactory
import com.example.smarttrade.classes.Certification
import com.example.smarttrade.classes.Price
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import kotlinx.coroutines.Deferred
import com.example.smarttrade.classes.typeofusers.Costumer
import kotlinx.coroutines.runBlocking


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

    fun getProducts() : List<Product> {
        var list : List<Product?>
        var listCer : List<Certification>
        var finalList = mutableListOf<Product>()
        runBlocking {
            list = call.getAllProducts().await()
            listCer = call.getUncertifiedCertificates().await()

            var idList = mutableListOf<Int>()

            for(certificate in listCer){
                idList.add(certificate.certification_id)
            }

            for(product in list){
                if(idList.indexOf(product!!.certificationId) == -1){
                    finalList.add(product)
                }
            }
        }
        return finalList

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

    fun createCostumer(costumer: Costumer) : User? {
        val user : User?
        runBlocking {
            user = call.createCostumer(costumer).await()
        }
        return user
    }
    fun getUncertifiedCertificates(): List<Product> {
        var list : List<Certification>
        var products : List<Product?>
        var finalList = mutableListOf<Product>()
        runBlocking {
            list = call.getUncertifiedCertificates().await()
            products = call.getAllProducts().await()
            var listId = mutableListOf<Int>()

            for(certificate in list){
                listId.add(certificate.certification_id)
            }

            for(product in products){
                if(listId.indexOf(product!!.certificationId) != -1){
                    finalList.add(product)
                }

            }
        }
        return finalList
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
}


