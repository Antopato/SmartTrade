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
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class HTTPcalls() {

    val idMario = "192.168.1.97"

    val myId = "192.168.1.59"
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
                         println(product.image)
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

    fun createCostumer(costumer: Costumer): Deferred<Costumer?> {
        println("Orchata")
        return CoroutineScope(Dispatchers.IO).async {
            val gson = Gson()
            val json = gson.toJson(costumer)
            println(json)
            println(costumer)
            val url = URL("http://$idMario:8080/clients/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")
            val outPutStream = BufferedWriter(OutputStreamWriter(connection.outputStream))
            outPutStream.write(json.toString())
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

    fun getComputerImage(urlString: String): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$myId:8080/products/$urlString")
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



            } else {
                println("Esto va mal en get image")
            }

        }
    }
}