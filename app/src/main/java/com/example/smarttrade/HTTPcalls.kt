package com.example.smarttrade

import com.example.smarttrade.classes.Certification
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import com.example.smarttrade.classes.electronic.Computer
import com.example.smarttrade.classes.typeofusers.Costumer
import com.example.smarttrade.classes.typeofusers.Merchant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json


class HTTPcalls() {

    val idMario = "192.168.0.20"

    val myId = "10.0.2.2"
    fun getUserById(mail : String) : Deferred<User?> {
       return CoroutineScope(Dispatchers.IO).async {
                println("Aquí al menos si "+ mail)
                val url = URL("http://$myId:8080/users/"+mail)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()
                println("He enviado la petición")
                val responseCode = connection.responseCode
                println(responseCode)

                println( connection.content.toString())
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String? = reader.readLine()
                    while (line != null) {
                        response.append(line)
                        line = reader.readLine()
                    }

                    if(response.isEmpty()){
                        println("Esto está vacío")
                        return@async null
                    }else {
                        val jsonResponse = response.toString()
                        println("json" + jsonResponse)
                        val gson = Gson()
                        val cliente: User = gson.fromJson(jsonResponse, User::class.java)
                        println(cliente.email + " " + cliente.password)
                        reader.close()

                        return@async cliente
                    }


                //return cliente

                } else {
                    println("Esto va mal")
                    return@async null

                }
        }

    }

    fun getAllProducts() : Deferred<List<Product>>{
         return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/products/certified")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode

             if (responseCode == HttpURLConnection.HTTP_OK) {
                 val inputStream = connection.inputStream
                 val reader = BufferedReader(InputStreamReader(inputStream))
                 val response = StringBuilder()
                 var line: String? = reader.readLine()
                 while (line != null) {
                     response.append(line)
                     line = reader.readLine()
                 }

                 if(response.isEmpty()){
                     println("Esto está vacío")
                     return@async emptyList()
                 }else {
                     val jsonResponse = response.toString()
                     println("json" + jsonResponse)
                     val gson = Gson()
                     val finallist = mutableListOf<Product>()
                     val list: List<Product> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                     for(product in list){
                         print(product.name)
                         finallist.add(product)
                     }
                     return@async finallist
                 }


                 //return cliente

             } else {
                 println("Esto va mal")
                 return@async emptyList()

             }

        }

    }

    fun createCostumer(costumer: Costumer): Deferred<User?> {
        println("Orchata")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/clients/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val balance = costumer.balance
            val birthdateString = costumer.birthdateString
            val email = costumer.email
            val name = costumer.name
            val password = costumer.password
            val type = "CLIENT"

            val requestBody = StringBuilder().apply {
                append("balance=$balance&")
                append("birthdateString=$birthdateString&")
                append("email=$email&")
                append("name=$name&")
                append("password=$password&")
                append("type=$type")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if(codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async costumer
            } else{
                return@async null
            }
        }
    }
    fun getUncertifiedCertificates(): Deferred<List<Product>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/products/uncertified")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val responseCode = connection.responseCode
            print(responseCode)

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String? = reader.readLine()
                while (line != null) {
                    response.append(line)
                    line = reader.readLine()
                }

                if(response.isEmpty()){
                    println("Esto está vacío")
                    return@async emptyList<Product>()
                }else {
                    val jsonResponse = response.toString()
                    println("json: $jsonResponse")
                    val gson = Gson()
                    val list: List<Product> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                    println(list)
                    println(list[0].certificationId)
                    reader.close()

                    return@async list
                }

            } else {
                println("Esto va mal")
                return@async emptyList<Product>()
            }
        }
    }

    fun getComputerImage(urlString: String): Deferred<ByteArray> {
        lateinit var bytes:ByteArray
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/products/$urlString")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            println("estableciendo conexion")
            connection.connect()
            val responseCode = connection.responseCode
            println(responseCode)

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = BufferedInputStream(connection.inputStream)
                bytes = inputStream.readBytes()
                return@async bytes
            } else {
                println("Esto va mal en get image")
                return@async ByteArray(0)
            }

        }
    }
    fun getCategoryProducts(stringUrl : String) : Deferred<List<Product>>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/products/$stringUrl")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode
            println(responseCode)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String? = reader.readLine()
                while (line != null) {
                    response.append(line)
                    line = reader.readLine()
                }

                if(response.isEmpty()){
                    println("Esto está vacío")
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val finallist = mutableListOf<Product>()
                    val list: List<Product> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                    for(product in list){
                        print(product.name)
                        finallist.add(product)
                    }
                    return@async finallist
                }


                //return cliente

            } else {
                println("Esto va mal")
                return@async emptyList()

            }

        }
    }

    fun updateCertification(id:Int,bool:Boolean,admin_id:String): Deferred<Unit>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/certification/save/$id");
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "PUT"
            connection.doOutput = true
            println("He enviado la petición")
            val requestBody = StringBuilder().apply {
                append("bool=$bool&")
                append("userId=$admin_id")
            }.toString()
            /*val outPutStream: OutputStream = connection.outputStream
            outPutStream.flush()
            outPutStream.close()*/
            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            connection.disconnect()
        }
    }
    fun createMerchant(merchant: Merchant): Deferred<User?> {
        println("MFI")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/merchants/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val email = merchant.email
            val enterprise_name = merchant.enterprise_name
            val name = merchant.name
            val password = merchant.password
            val type = "MERCHANT"

            val requestBody = StringBuilder().apply {
                append("email=$email&")
                append("enterprise_name=$enterprise_name&")
                append("name=$name&")
                append("password=$password&")
                append("type=$type")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if(codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async merchant
            } else{
                return@async null
            }
        }
    }

    fun createComputer(computer: Computer, email: String): Deferred<Computer?> {
        println("Computer")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/electronics/computer/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val brand = computer.brand
            val description = computer.description
            val guarrantee = computer.guarrantee
            val image = computer.image
            val name = computer.name
            val operatingSystem = computer.operatingSystem
            val ownerid = email
            val price = computer.price
            val production = computer.production
            val ram = computer.ram
            val stock = computer.stock
            val storageType = computer.storageType


            val requestBody = StringBuilder().apply {
                append("brand=$brand&")
                append("description=$description&")
                append("guarrantee=$guarrantee&")
                append("image=$image&")
                append("name=$name&")
                append("operatingSystem=$operatingSystem&")
                append("ownerid=$ownerid&")
                append("price=$price&")
                append("production=$production&")
                append("ram=$ram&")
                append("stock=$stock&")
                append("storageType=$storageType")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if(codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async computer
            } else{
                return@async null
            }
        }
    }

    fun bitmapToFile(bitmap: Bitmap, directory: File, fileName: String): File {
        val file = File(directory, fileName)

        val fos = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)

        fos.flush()
        fos.close()

        return file
    }
}