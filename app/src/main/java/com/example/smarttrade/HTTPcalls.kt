package com.example.smarttrade

import com.example.smarttrade.classes.Address
import com.example.smarttrade.classes.CreditCard
import com.example.smarttrade.classes.Order
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


import java.io.DataOutputStream

//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json


class HTTPcalls() {

    val idMario = "192.168.1.97"

    val myId = "10.0.2.2"
    fun getUserById(mail : String) : Deferred<User?>{
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
            val url = URL("http://$idMario:8080/products/pending")
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

    fun getCertificates(user: User): Deferred<List<Product>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/certified/notSelf/${user.email}")
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
                append("owner=${seller.seller_email}&")
                append("price=${seller.price}&")
                append("stock=${seller.stock}")
            }.toString()
            println(requestBody)
            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()
            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            println("Este es el bueno, sisi")
            if(codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async seller
            } else{
                return@async null
            }
        }
    }

    fun getCreditCardsByUser(email: String): Deferred<List<CreditCard>>{
        return CoroutineScope(Dispatchers.IO).async {
            val connection = connect("http://$idMario:8080/creditCards/creditCard/$email", "GET")


            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val output = getOutput(connection)

                if(output.isEmpty()){
                    println("Esto está vacío")
                    return@async emptyList()
                }else {
                    val jsonResponse = output.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<CreditCard> = gson.fromJson(jsonResponse, object : TypeToken<List<CreditCard>>() {}.type)
                    return@async list
                }

            } else {
                println("Esto va mal")
                return@async emptyList()
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
    ) : Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://$idMario:8080/products/electronics/computer/add"
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
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/electronics/smartphone/add"
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
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/electronics/household/add"
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
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/fashion/fashiontop/add"
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
        size: Int,
        stock: Int,
        botType: String
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/fashion/fashionbot/add"
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
        size: Int,
        stock: Int,
        footwearType: String
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/fashion/footwear/add"
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
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/food/drink/add"
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
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/food/fish/add"
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
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/food/fruit/add"
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
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/food/meat/add"
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
    ): Deferred<Unit?> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = "http://10.0.2.2:8080/products/food/vegetable/add"
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
    }

    fun getMerchantProductsById(id: String) : Deferred<List<Product?>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/merchant/$id")
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
            val url = URL("http://$idMario:8080/products/sellers/$productId")
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
                    println(list.get(0).price)
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
            val url = URL("http://$idMario:8080/shoppingCart/$id")
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
            val url = URL("http://$idMario:8080/products/"+id)
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
            val url = URL("http://$idMario:8080/shoppingCart/delete/product")
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
            val url = URL("http://$idMario:8080/shoppingCart/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connect()
            val quantity = 1
            val requestBody = StringBuilder().apply {
                append("price=${sell.price}&")
                append("product_id=${sell.id_selled_by}&")
                append("quantity=$quantity&")
                append("shopping_cart_owner=$mail")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if(codigoRespuesta==409){
                println("Código de respuesta "+ codigoRespuesta)
                return@async -1
            }else{return@async codigoRespuesta}
        }
    }
    fun getWhisList(id: String) : Deferred<List<Product>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/wishList/$id")
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
    fun getForLaterList(id: String) : Deferred<List<Sell>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/savedForLater/$id")
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
    fun addProducToWhislist(userId : String, productId : Int) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/wishList/add")
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

            val connection = connect("http://$idMario:8080/savedForLater/add", "POST")

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
            val url = URL("http://$idMario:8080/wishList/delete/product")
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

            val connection = connect("http://$idMario:8080/savedForLater/delete/product","DELETE")

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

    fun getAvgPrice(id : Int) : Deferred<Double>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/products/averagePrice/$id","GET")

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line = reader.readLine()

            val value = line.toDouble()

            return@async value
        }
    }
    fun getValoration(id : Int): Deferred<Double>{
        return CoroutineScope(Dispatchers.IO).async{
            println(id)
            val connection = connect("http://$idMario:8080/valoration/$id","GET")

            println("${connection.responseCode}:${connection.responseMessage}")

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line = reader.readLine()
            println(line)
            try {
                val value = line.toDouble()
                return@async value

            }catch(e : Exception){
                return@async 0.0
            }

        }
    }

    fun deleteAllShopping(email:String) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/shoppingCart/delete/$email","DELETE")
            val value = connection.responseCode
            return@async value
        }
    }

    fun deleteAllWhislist(email:String) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/wishList/delete/$email","DELETE")
            val value = connection.responseCode
            return@async value
        }
    }

    fun deleteAllForLater(email:String) : Deferred<Int> {
        return CoroutineScope(Dispatchers.IO).async {
            val connection = connect("http://$idMario:8080/savedForLater/delete/$email", "DELETE")
            val value = connection.responseCode
            return@async value
        }
    }

    fun saveCart(cart : ShoppingCart) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/shoppingCart/updateQuantity","PUT")
            val requestBody = StringBuilder().apply {
                append("email=${cart.shopping_cart_owner}&")
                append("id=${cart.product_id}&")
                append("quantity=${cart.quantity}")
            }.toString()

            publish(connection, requestBody)

            return@async connection.responseCode
        }
    }

    fun getAddresses(email : String) : Deferred<List<Address>>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/addres/${email}","GET")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
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
                    val list: List<Address> = gson.fromJson(jsonResponse, object : TypeToken<List<Address>>() {}.type)
                    println(list)
                    reader.close()

                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }

    fun addAddress(address : Address):Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/addres/add","POST")
            println(address.city)
            val requestBody = StringBuilder().apply {
                append("city=${address.city}&")
                append("postalCode=${address.postalCode}&")
                append("province=${address.province}&")
                append("street=${address.street}&")
                append("addresOf=${address.addresOf}")
            }.toString()
            println("añadiendo address "+ connection.responseCode)

            publish(connection, requestBody)

            return@async connection.responseCode
        }
    }

    fun getSellById(id : Int) : Deferred<Sell?>{
        return CoroutineScope(Dispatchers.IO).async {

            val connection = connect("http://$idMario:8080/products/selledBy/${id}","GET")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {

                val response = getOutput(connection)

                if(response.isEmpty()){
                    println("Esto está vacío")
                    return@async null
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val product : Sell = gson.fromJson(jsonResponse, Sell::class.java)
                    return@async product
                }
            } else {
                println("Esto va mal "+ connection.responseCode)
                return@async null

            }
        }

    }

    fun addCreditCard(card : CreditCard) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/creditCards/add","POST")

            val requestBody = StringBuilder().apply {
                append("card_number=${card.cardNumber}&")
                append("CVV=${card.CVV}&")
                append("credit_card_owner=${card.owner}&")
                append("expiringDateString=${card.expirationDate}&")
            }.toString()


            publish(connection, requestBody)

            return@async connection.responseCode
        }
    }

    fun createOrder(client: String): Deferred<Order?>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/order/createOrder","POST")

            val requestBody = StringBuilder().apply {
                append("client=$client")
            }.toString()

            publish(connection, requestBody)

            val response = getOutput(connection)
            if(response.isEmpty()){
                return@async null
            }else {
                val jsonResponse = response.toString()
                println("json" + jsonResponse)
                val gson = Gson()
                val order : Order = gson.fromJson(jsonResponse, Order::class.java)
                return@async order
            }
        }
    }

    fun addProductToOrder(orderId: Int, productId : Int) : Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/order/${orderId}/add","POST")

            val requestBody = StringBuilder().apply {
                append("orderId=$orderId&")
                append("productId=$productId")
            }.toString()

            publish(connection, requestBody)

            return@async connection.responseCode
        }
    }

    fun getOrdersById(id: String): Deferred<List<Order>>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/order/findByEmail","GET")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = getOutput(connection)

                if(response.isEmpty()){
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Order> = gson.fromJson(jsonResponse, object : TypeToken<List<Order>>() {}.type)
                    println(list)
                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }

    fun getProductsByOrderId(orderId: Int): Deferred<List<Product>>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/order/$orderId/products","GET")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = getOutput(connection)

                if(response.isEmpty()){
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Product> = gson.fromJson(jsonResponse, object : TypeToken<List<Product>>() {}.type)
                    println(list)
                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }

    fun getMerchantOrders(email: String): Deferred<List<Order>>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/order/filterFirstProduct","GET")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = getOutput(connection)

                if(response.isEmpty()){
                    return@async emptyList()
                }else {
                    val jsonResponse = response.toString()
                    println("json" + jsonResponse)
                    val gson = Gson()
                    val list: List<Order> = gson.fromJson(jsonResponse, object : TypeToken<List<Order>>() {}.type)
                    println(list)
                    return@async list
                }
            } else {
                return@async emptyList()
            }
        }
    }

    fun notifyState(orderId: Int, state: String): Deferred<Int>{
        return CoroutineScope(Dispatchers.IO).async{
            val connection = connect("http://$idMario:8080/order/udateState","PUT")
            val requestBody = StringBuilder().apply {
                append("order_id=$orderId&")
                append("state=$state")
            }.toString()

            publish(connection, requestBody)

            return@async connection.responseCode
        }
    }




    fun publish(connection : HttpURLConnection, requestBody : String){
        println(requestBody)
        val outPutStream = OutputStreamWriter(connection.outputStream)
        outPutStream.write(requestBody)
        outPutStream.flush()
        outPutStream.close()
    }

    fun connect(url : String, type : String) : HttpURLConnection{
        val url = URL(url)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = type
        connection.connect()

        return connection
    }

    fun getOutput(connection : HttpURLConnection) : StringBuilder{
        val inputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuilder()
        var line: String? = reader.readLine()

        while (line != null) {
            response.append(line)
            line = reader.readLine()
        }

        reader.close()

        return response
    }


}