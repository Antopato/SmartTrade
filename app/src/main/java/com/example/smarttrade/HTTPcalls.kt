package com.example.smarttrade

import com.example.smarttrade.classes.Certification
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User
import com.example.smarttrade.classes.typeofusers.Costumer
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


                //return cliente

                } else {
                    println("Esto va mal")
                    return@async null

                }
        }

    }

    fun getAllProducts() : Deferred<List<Product>>{
         return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products")
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
            } else{
                return@async null
            }
        }
    }
    fun getUncertifiedCertificates(): Deferred<List<Certification>> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/certification/uncertified")
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
                    return@async emptyList<Certification>()
                }else {
                    val jsonResponse = response.toString()
                    println("json: $jsonResponse")
                    val gson = Gson()
                    val list: List<Certification> = gson.fromJson(jsonResponse, object : TypeToken<List<Certification>>() {}.type)
                    println(list)
                    println(list[0].certification_id)
                    reader.close()

                    return@async list
                }

            } else {
                println("Esto va mal")
                return@async emptyList<Certification>()
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
            val url = URL("http://$myId:8080/certification/save/$bool$id$admin_id")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")
            /*val outPutStream: OutputStream = connection.outputStream
            outPutStream.flush()
            outPutStream.close()*/

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            connection.disconnect()
        }
    }
}