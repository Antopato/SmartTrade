package com.example.smarttrade

import com.example.smarttrade.classes.Product
import junit.framework.TestCase.assertEquals
import org.json.JSONObject
import org.junit.Test

class FilterTest() {
    val service = CatalogActivity()

    @Test
    fun filterMinPrice() {
        var prod1= Product(1,"Lavadora","Lava muy bien","En masa","","",1,"Samsung","Plastico","HOUSEHOLD")
        var prod2= Product(2,"Lavadora","Lava muy bien","En masa","","",1,"Samsung","Plastico","HOUSEHOLD")
        var prod3= Product(3,"Lavadora","Lava muy bien","En masa","","",1,"Samsung","Plastico","HOUSEHOLD")

        var productList = mutableListOf<Product>()
        productList.add(prod1)
        productList.add(prod2)
        productList.add(prod3)

        service.binding.minimumValue.text = "700€"

        var jsonPrice = JSONObject()
        jsonPrice.put(prod1.productId.toString(),300)
        jsonPrice.put(prod2.productId.toString(),600)
        jsonPrice.put(prod3.productId.toString(),900)

        val result = service.setMinimumValue(productList,jsonPrice)

        assertEquals(1,result.count())
    }


    @Test
    fun filterMaxPrice() {
        var prod1= Product(1,"Lavadora","Lava muy bien","En masa","","",1,"Samsung","Plastico","HOUSEHOLD")
        var prod2= Product(2,"Lavadora","Lava muy bien","En masa","","",1,"Samsung","Plastico","HOUSEHOLD")
        var prod3= Product(3,"Lavadora","Lava muy bien","En masa","","",1,"Samsung","Plastico","HOUSEHOLD")

        var productList = mutableListOf<Product>()
        productList.add(prod1)
        productList.add(prod2)
        productList.add(prod3)

        service.binding.seekvalue.text = "700€"

        var jsonPrice = JSONObject()
        jsonPrice.put(prod1.productId.toString(),300)
        jsonPrice.put(prod2.productId.toString(),600)
        jsonPrice.put(prod3.productId.toString(),900)

        val result = service.setMaximumValue(productList,jsonPrice)

        assertEquals(2,result.count())
    }
}