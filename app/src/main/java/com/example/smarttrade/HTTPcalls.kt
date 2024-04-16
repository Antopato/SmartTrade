package com.example.smarttrade

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



    fun updateCertification(id:Int,bool:Boolean,admin_id:String): Deferred<Unit> {
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
    
    fun createPhone(phone: SmartPhone, email: String): Deferred<SmartPhone?> {
        println("Computer")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/electronics/smartphone/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val brand = phone.brand
            val description = phone.description
            val display = phone.display
            val guarrantee = phone.guarantee
            val materials = phone.materials
            val name = phone.name
            val ownerId = email
            val price = phone.price
            val processor = phone.processor
            val production = phone.production
            val size = phone.size
            val stock = phone.stock



            val requestBody = StringBuilder().apply {
                append("brand=$brand&")
                append("description=$description&")
                append("display=$display&")
                append("guarantee=$guarrantee&")
                append("materials=$materials&")
                append("name=$name&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("processor=$processor&")
                append("production=$production&")
                append("size=$size&")
                append("stock=$stock")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if(codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async phone
            } else{
                return@async null
            }
        }
    }

    fun createHousehold(household: HouseHold, email: String): Deferred<HouseHold?> {
        println("HOusehold")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/electronics/household/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val brand = household.brand
            val description = household.description
            val guarrantee = household.guarantee
            val materials = household.materials
            val name = household.name
            val noiseLevel = household.noiseLevel
            val ownerId = email
            val powerConsumption = household.powerConsumption
            val price = household.price
            val production = household.production
            val stock = household.stock

            val requestBody = StringBuilder().apply {
                append("brand=$brand&")
                append("description=$description&")
                append("guarantee=$guarrantee&")
                append("materials=$materials&")
                append("name=$name&")
                append("noiseLevel=$noiseLevel&")
                append("ownerId=$ownerId&")
                append("powerConsumption=$powerConsumption&")
                append("price=$price&")
                append("production=$production&")
                append("stock=$stock")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if(codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async household
            } else{
                return@async null
            }
        }
    }

    fun createFashiontop(top: FashionTop, email: String): Deferred<FashionTop?> {
        println("Fashiontop")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/fashion/fashiontop/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val brand = top.brand
            val description = top.description
            val materials = top.materials
            val name = top.name
            val ownerId = email
            val price = top.price
            val production = top.production
            val size = top.size
            val stock = top.stock
            val topType = top.topType

            val requestBody = StringBuilder().apply {
                append("brand=$brand&")
                append("description=$description&")
                append("materials=$materials&")
                append("name=$name&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("production=$production&")
                append("size=$size&")
                append("stock=$stock&")
                append("topType=$topType")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async top
            } else {
                return@async null
            }
        }
    }

    fun createFashionbot(bot: FashionBot, email: String): Deferred<FashionBot?> {
        println("fashionbot")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/fashion/fashionbot/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val brand = bot.brand
            val description = bot.description
            val materials = bot.materials
            val name = bot.name
            val ownerId = email
            val price = bot.price
            val production = bot.production
            val size = bot.size
            val stock = bot.stock
            val topType = bot.botType

            val requestBody = StringBuilder().apply {
                append("brand=$brand&")
                append("botType=$topType")
                append("description=$description&")
                append("materials=$materials&")
                append("name=$name&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("production=$production&")
                append("size=$size&")
                append("stock=$stock&")

            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async bot
            } else {
                return@async null
            }
        }
    }

    fun createFootwear(footwear: FootWear, email: String): Deferred<FootWear?> {
        println("Footwear")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/fashion/footwear/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val brand = footwear.brand
            val description = footwear.description
            val image = footwear.image
            val materials = footwear.materials
            val name = footwear.name
            val ownerId = email
            val price = footwear.price
            val production = footwear.production
            val size = footwear.size
            val stock = footwear.stock
            val footwearType = footwear.footwearType

            val requestBody = StringBuilder().apply {
                append("brand=$brand&")
                append("description=$description&")
                append("footwearType=$footwearType")
                append("materials=$materials&")
                append("name=$name&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("production=$production&")
                append("size=$size&")
                append("stock=$stock&")

            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async footwear
            } else {
                return@async null
            }
        }
    }

    fun createFruit(fruit: Fruit, email: String): Deferred<Fruit?> {
        println("Fruit")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/food/fruit/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val calories = fruit.calories
            val date = fruit.expiringDate
            val description = fruit.description
            val flavor = fruit.flavor
            val name = fruit.name
            val ownerId = email
            val price = fruit.price
            val production = fruit.production
            val quantity = fruit.quantity
            val stock = fruit.stock
            val unit = fruit.unit

            val requestBody = StringBuilder().apply {
                append("calories=$calories&")
                append("date=$date&")
                append("description=$description&")
                append("flavor=$flavor&")
                append("name=$name&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("production=$production&")
                append("quantity=$quantity&")
                append("stock=$stock&")
                append("unit=$unit")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async fruit
            } else {
                return@async null
            }
        }
    }

    fun createFish(fish: Fish, email: String): Deferred<Fish?> {
        println("Fish")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/food/fish/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val calories = fish.calories
            val date = fish.expiringDate
            val description = fish.description
            val fishingMethod = fish.fishingMethod
            val name = fish.name
            val ownerId = email
            val price = fish.price
            val production = fish.production
            val quantity = fish.quantity
            val stock = fish.stock
            val unit = fish.unit

            val requestBody = StringBuilder().apply {
                append("calories=$calories&")
                append("date=$date&")
                append("description=$description&")
                append("fishingMethod=$fishingMethod&")
                append("name=$name&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("production=$production&")
                append("quantity=$quantity&")
                append("stock=$stock&")
                append("unit=$unit")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async fish
            } else {
                return@async null
            }
        }
    }
    //incopleto
    fun createMeat(meat: Meat, email: String): Deferred<Meat?> {
        println("Meat")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/food/meat/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val calories = meat.calories
            val date = meat.expiringDate
            val description = meat.description
            val name = meat.name
            val ownerId = email
            val price = meat.price
            val production = meat.production
            val quantity = meat.quantity
            val stock = meat.stock
            val unit = meat.unit

            val requestBody = StringBuilder().apply {
                append("calories=$calories&")
                append("date=$date&")
                append("description=$description&")
                append("name=$name&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("production=$production&")
                append("quantity=$quantity&")
                append("stock=$stock&")
                append("unit=$unit")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async meat
            } else {
                return@async null
            }
        }
    }

    fun createVegetables(vegetable: Vegetable, email: String): Deferred<Vegetable?> {
        println("Vegetables")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/food/vegetables/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val calories = vegetable.calories
            val date = vegetable.expiringDate
            val description = vegetable.description
            val name = vegetable.name
            val origin = vegetable.origin
            val ownerId = email
            val price = vegetable.price
            val production = vegetable.production
            val quantity = vegetable.quantity
            val season = vegetable.season
            val stock = vegetable.stock
            val unit = vegetable.unit

            val requestBody = StringBuilder().apply {
                append("calories=$calories&")
                append("date=$date&")
                append("description=$description&")
                append("name=$name&")
                append("origin=$origin&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("production=$production&")
                append("quantity=$quantity&")
                append("season=$season&")
                append("stock=$stock&")
                append("unit=$unit")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async vegetable
            } else {
                return@async null
            }
        }
    }

    fun createDrink(drink: Drink, email: String): Deferred<Drink?> {
        println("drink")
        return CoroutineScope(Dispatchers.IO).async {
            val url = URL("http://$idMario:8080/products/food/drink/add")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            println("He enviado la petición")
            connection.doOutput = true
            val alcohol = drink.alcohol
            val calories = drink.calories
            val date = drink.expiringDate
            val description = drink.description
            val driktype = drink.drinkType
            val name = drink.name
            val ownerId = email
            val price = drink.price
            val production = drink.production
            val quantity = drink.quantity
            val stock = drink.stock
            val unit = drink.unit

            val requestBody = StringBuilder().apply {
                append("alcohol=$alcohol&")
                append("calories=$calories&")
                append("date=$date&")
                append("description=$description&")
                append("driktype=$driktype&")
                append("name=$name&")
                append("ownerId=$ownerId&")
                append("price=$price&")
                append("production=$production&")
                append("quantity=$quantity&")
                append("stock=$stock&")
                append("unit=$unit")
            }.toString()

            val outPutStream = OutputStreamWriter(connection.outputStream)
            outPutStream.write(requestBody)
            outPutStream.flush()
            outPutStream.close()

            val codigoRespuesta = connection.responseCode
            println(codigoRespuesta)
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                return@async drink
            } else {
                return@async null
            }
        }
    }
}