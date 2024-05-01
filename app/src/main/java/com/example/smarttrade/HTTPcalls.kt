package com.example.smarttrade

import com.example.smarttrade.classes.Certification
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.ShoppingCart
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
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
import java.io.DataOutputStream

//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json


class HTTPcalls() {

    val idMario = "192.168.0.20"

    val myId = "192.168.0.21"
    fun getUserById(mail : String) : Deferred<User?> {
       return CoroutineScope(Dispatchers.IO).async {
                println("Aquí al menos si "+ mail)
                val url = URL("http://$idMario:8080/users/"+mail)
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
                } else {
                    println("Esto va mal")
                    return@async null

                }
        }

    }

    fun getAllProducts() : Deferred<List<Product>>{
         return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/certified")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode
             println("Response de products " + responseCode)

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
            val url = URL("http://$idMario:8080/clients/add")
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
            }else{
                return@async null
            }
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
    fun getUncertifiedCertificates(): Deferred<List<Product>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/uncertified")
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
            val url = URL("http://$idMario:8080/products/$urlString")
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
            val url = URL("http://$idMario:8080/products/$stringUrl")
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



    fun updateCertification(id:Int,bool:Boolean,admin_id:String): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/certification/save/$id");
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

    fun copyProduct(seller: Sell): Deferred<Sell?>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/saveexistend/${seller.id_product}")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            val requestBody = StringBuilder().apply {
                append("productId=${seller.id_product}&")
                append("owner=${seller.id_selled_by}&")
                append("price=${seller.price}&")
                append("stock=${seller.stock}")
            }.toString()
            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()
            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if(codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async seller
            } else{
                return@async null
            }
        }
    }
    fun createComputer(
        byteArray: ByteArray,
        brand: String,
        description: String,
        guarrantee: Int,
        materials: String,
        name: String,
        operatingSystem: String,
        ownerId: String,
        price: Double,
        production: String,
        ram: Int,
        stock: Int,
        storageType: String
    ): Deferred<Computer?> {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"brand\"\r\n\r\n")
        outputStream.writeBytes("$brand\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"guarrantee\"\r\n\r\n")
        outputStream.writeBytes("$guarrantee\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"materials\"\r\n\r\n")
        outputStream.writeBytes("$materials\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"operatingSystem\"\r\n\r\n")
        outputStream.writeBytes("$operatingSystem\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ram\"\r\n\r\n")
        outputStream.writeBytes("$ram\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"storageType\"\r\n\r\n")
        outputStream.writeBytes("$storageType\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createSmartphone(
        byteArray: ByteArray,
        brand: String,
        description: String,
        display: String,
        guarrantee: Int,
        materials: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        processor: String,
        stock: Int,
        size: Double
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"brand\"\r\n\r\n")
        outputStream.writeBytes("$brand\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"display\"\r\n\r\n")
        outputStream.writeBytes("$display\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"guarrantee\"\r\n\r\n")
        outputStream.writeBytes("$guarrantee\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"materials\"\r\n\r\n")
        outputStream.writeBytes("$materials\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")


        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"processor\"\r\n\r\n")
        outputStream.writeBytes("$processor\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"size\"\r\n\r\n")
        outputStream.writeBytes("$size\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createHousehold(
        byteArray: ByteArray,
        brand: String,
        description: String,
        guarrantee: Int,
        materials: String,
        name: String,
        noiseLevel: Double,
        ownerId: String,
        price: Double,
        production: String,
        powerConsumption: Int,
        stock: Int,
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"brand\"\r\n\r\n")
        outputStream.writeBytes("$brand\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"guarrantee\"\r\n\r\n")
        outputStream.writeBytes("$guarrantee\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"materials\"\r\n\r\n")
        outputStream.writeBytes("$materials\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"noiseLevel\"\r\n\r\n")
        outputStream.writeBytes("$noiseLevel\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"powerConsumption\"\r\n\r\n")
        outputStream.writeBytes("$powerConsumption\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")


        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")


        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createFashionTop(
        byteArray: ByteArray,
        brand: String,
        description: String,
        materials: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        size: String,
        stock: Int,
        topType: String
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"brand\"\r\n\r\n")
        outputStream.writeBytes("$brand\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"materials\"\r\n\r\n")
        outputStream.writeBytes("$materials\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"size\"\r\n\r\n")
        outputStream.writeBytes("$size\r\n")


        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"topType\"\r\n\r\n")
        outputStream.writeBytes("$topType\r\n")


        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createFashionBottom(
        byteArray: ByteArray,
        brand: String,
        description: String,
        materials: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        size: String,
        stock: Int,
        botType: String
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"botType\"\r\n\r\n")
        outputStream.writeBytes("$botType\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"brand\"\r\n\r\n")
        outputStream.writeBytes("$brand\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"materials\"\r\n\r\n")
        outputStream.writeBytes("$materials\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"size\"\r\n\r\n")
        outputStream.writeBytes("$size\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createFootWear(
        byteArray: ByteArray,
        brand: String,
        description: String,
        materials: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        size: String,
        stock: Int,
        footwearType: String
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"brand\"\r\n\r\n")
        outputStream.writeBytes("$brand\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"footwearType\"\r\n\r\n")
        outputStream.writeBytes("$footwearType\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"materials\"\r\n\r\n")
        outputStream.writeBytes("$materials\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"size\"\r\n\r\n")
        outputStream.writeBytes("$size\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createDrink(
        byteArray: ByteArray,
        alcohol: Int,
        calories: Int,
        date: String,
        drinktype: String,
        description: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        quantity: Int,
        stock: Int,
        unit: String
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"alcohol\"\r\n\r\n")
        outputStream.writeBytes("$alcohol\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"calories\"\r\n\r\n")
        outputStream.writeBytes("$calories\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"date\"\r\n\r\n")
        outputStream.writeBytes("$date\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"drinktype\"\r\n\r\n")
        outputStream.writeBytes("$drinktype\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"quantity\"\r\n\r\n")
        outputStream.writeBytes("$quantity\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"unit\"\r\n\r\n")
        outputStream.writeBytes("$unit\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createFish(
        byteArray: ByteArray,
        calories: Int,
        date: String,
        fishingMethod: String,
        description: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        quantity: Int,
        stock: Int,
        unit: String
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"calories\"\r\n\r\n")
        outputStream.writeBytes("$calories\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"date\"\r\n\r\n")
        outputStream.writeBytes("$date\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"fishingMethod\"\r\n\r\n")
        outputStream.writeBytes("$fishingMethod\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"quantity\"\r\n\r\n")
        outputStream.writeBytes("$quantity\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"unit\"\r\n\r\n")
        outputStream.writeBytes("$unit\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createFruit(
        byteArray: ByteArray,
        calories: Int,
        date: String,
        flavor: String,
        description: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        quantity: Int,
        stock: Int,
        unit: String
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"calories\"\r\n\r\n")
        outputStream.writeBytes("$calories\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"date\"\r\n\r\n")
        outputStream.writeBytes("$date\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"flavor\"\r\n\r\n")
        outputStream.writeBytes("$flavor\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"quantity\"\r\n\r\n")
        outputStream.writeBytes("$quantity\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"unit\"\r\n\r\n")
        outputStream.writeBytes("$unit\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createMeat(
        byteArray: ByteArray,
        calories: Int,
        date: String,
        origin: String,
        description: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        quantity: Int,
        stock: Int,
        unit: String
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"calories\"\r\n\r\n")
        outputStream.writeBytes("$calories\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"date\"\r\n\r\n")
        outputStream.writeBytes("$date\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"origin\"\r\n\r\n")
        outputStream.writeBytes("$origin\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"quantity\"\r\n\r\n")
        outputStream.writeBytes("$quantity\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"unit\"\r\n\r\n")
        outputStream.writeBytes("$unit\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun createVegetable(
        byteArray: ByteArray,
        calories: Int,
        date: String,
        origin: String,
        description: String,
        name: String,
        ownerId: String,
        price: Double,
        production: String,
        quantity: Int,
        season: String,
        stock: Int,
        unit: String
    ) {
        val url = "http://10.0.2.2:8080/products/electronics/computer/add"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.useCaches = false
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary")

        val outputStream = DataOutputStream(connection.outputStream)
        val boundary = "---------------------------boundary"

        // Agregar parámetros de la solicitud
        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"calories\"\r\n\r\n")
        outputStream.writeBytes("$calories\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"date\"\r\n\r\n")
        outputStream.writeBytes("$date\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n")
        outputStream.writeBytes("$description\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"imagen.jpg\"\r\n")
        outputStream.writeBytes("Content-Type: image/jpeg\r\n\r\n") // Cambiar a image/jpeg
        outputStream.write(byteArray)
        outputStream.writeBytes("\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n")
        outputStream.writeBytes("$name\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"origin\"\r\n\r\n")
        outputStream.writeBytes("$origin\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"ownerId\"\r\n\r\n")
        outputStream.writeBytes("$ownerId\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
        outputStream.writeBytes("$price\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"production\"\r\n\r\n")
        outputStream.writeBytes("$production\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"quantity\"\r\n\r\n")
        outputStream.writeBytes("$quantity\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"season\"\r\n\r\n")
        outputStream.writeBytes("$season\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"stock\"\r\n\r\n")
        outputStream.writeBytes("$stock\r\n")

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"unit\"\r\n\r\n")
        outputStream.writeBytes("$unit\r\n")

        outputStream.writeBytes("--$boundary--\r\n")

        outputStream.flush()
        outputStream.close()

        // Leer y mostrar la respuesta del servidor
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedInputStream(connection.inputStream)
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Respuesta del servidor: $response")
        } else {
            val errorStream = BufferedInputStream(connection.errorStream)
            val error = errorStream.bufferedReader().use { it.readText() }
            println("Error en la petición: $error")
        }
    }

    fun getMerchantProductsById(id: String) : Deferred<List<Product?>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/$id")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
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
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Product?> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                    println(list)
                    reader.close()

                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }
    fun getMerchantProductsPending(id: String) : Deferred<List<Product?>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/$id/pending")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
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
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Product?> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                    println(list)
                    reader.close()

                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }
    fun getMerchantProductsDenied(id: String) : Deferred<List<Product?>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/$id/denied")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
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
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Product?> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                    println(list)
                    reader.close()

                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }
    fun getSeller(productId : Int) : Deferred<List<Sell>>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/products/sellers/$productId")
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
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Sell> = gson.fromJson(jsonResponse, object : TypeToken<List<Sell>>() {}.type)
                    println(list)
                    reader.close()

                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }
    fun getCartById(id : String) : Deferred<List<ShoppingCart>>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/shoppingCart/$id")
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
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<ShoppingCart> = gson.fromJson(jsonResponse, object : TypeToken<List<ShoppingCart>>() {}.type)
                    println(list)
                    reader.close()

                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }
    fun getProductById(id : Int) : Deferred<Product?>{
        return CoroutineScope(Dispatchers.IO).async {
            println("Productos por id "+id)
            val url = URL("http://$myId:8080/products/"+id)
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
                    val product : Product = gson.fromJson(jsonResponse, Product::class.java)
                    reader.close()
                    return@async product
                }
            } else {
                println("Esto va mal")
                return@async null

            }
        }

    }

    fun deleteProdFromCart(id : Int, email : String) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/shoppingCart/delete/product")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"
            connection.connect()

            val requestBody = StringBuilder().apply {
                append("id=$id&")
                append("email=$email")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)

            return@async codigoRespuesta
        }

    }

    fun addProdToCart(sell : Sell, mail : String) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/shoppingCart/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connect()
            val quantity = 1
            val requestBody = StringBuilder().apply {
                append("price=${sell.price.toInt()}&")
                append("product_id=${sell.id_product}&")
                append("quantity=$quantity&")
                append("shopping_cart_owner=$mail")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)

            return@async codigoRespuesta
        }
    }
    fun getWhisList(id: String) : Deferred<List<Product>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/wishList/$id")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
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
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Product> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                    println(list)
                    reader.close()

                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }
    fun getForLaterList(id: String) : Deferred<List<Product>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/savedForLater/$id")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
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
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Product> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                    println(list)
                    reader.close()

                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }
    fun addProducToWhislist(userId : String, productId : Int) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/wishList/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connect()
            val quantity = 1
            val requestBody = StringBuilder().apply {
                append("product_id=$productId&")
                append("wishList_owner=$userId")
            }.toString()

            publish(connection,requestBody)

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)

            return@async codigoRespuesta
        }
    }
    fun addProductToForLater(userId : String, productId : Int) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/savedForLater/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connect()

            val requestBody = StringBuilder().apply {
                append("product_id=$productId&")
                append("list_owner=$userId&")
            }.toString()

            publish(connection, requestBody)

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)

            return@async codigoRespuesta
        }
    }

    fun deleteProdFromWhislist(id : Int, email : String) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/wishList/delete/product")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"
            connection.connect()

            val requestBody = StringBuilder().apply {
                append("id=$id&")
                append("email=$email")
            }.toString()

            publish(connection, requestBody)

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)

            return@async codigoRespuesta
        }

    }

    fun deleteProdFromForLater(id : Int, email : String) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/savedForLater/delete/product")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"
            connection.connect()

            val requestBody = StringBuilder().apply {
                append("id=$id&")
                append("email=$email")
            }.toString()

            publish(connection,requestBody)

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)

            return@async codigoRespuesta
        }

    }




    fun publish(connection : HttpURLConnection, requestBody : String){
        val outPutStream = OutputStreamWriter(connection.outputStream)
        outPutStream.write(requestBody)
        outPutStream.flush()
        outPutStream.close()
    }
}