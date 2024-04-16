package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.drawToBitmap
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
import java.io.File


class AddProductActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {


    lateinit var updateImage : ImageView

    val service = BusinessLogic()
    val callsService = HTTPcalls()



    val pickMedia = registerForActivityResult(PickVisualMedia()){ uri ->
        if(uri!=null){
            updateImage.setImageURI(uri)
        }else{

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_product)
        val user = intent.getSerializableExtra("user") as User

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        updateImage = findViewById<ImageView>(R.id.uploadImage)
        val addProductbutt = findViewById<Button>(R.id.buttonAddProduct)

        val spinner: Spinner = findViewById(R.id.spinnerCategories)
        val options = resources.getStringArray(R.array.options)

        val name = findViewById<EditText>(R.id.editTextName)
        val price = findViewById<EditText>(R.id.editTextPrice)
        val seller = findViewById<EditText>(R.id.editTextCompany)
        val brand = findViewById<EditText>(R.id.editTextBrand)
        val description = findViewById<EditText>(R.id.editTextDescription)
        val material = findViewById<EditText>(R.id.editTextMaterials)
        val production = findViewById<EditText>(R.id.editTextProduction)
        val additionalInfo = findViewById<EditText>(R.id.editTextAdditionalInfo)

        val adicional1 = findViewById<EditText>(R.id.editText1)
        val adicional2 = findViewById<EditText>(R.id.editText2)
        val adicional3 = findViewById<EditText>(R.id.editText3)
        val adicional4 = findViewById<EditText>(R.id.editText4)
        val adicional5 = findViewById<EditText>(R.id.editText5)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinner.adapter = adapter

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }


        updateImage.setOnClickListener() {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        spinner.onItemSelectedListener = this


        addProductbutt.setOnClickListener() {
            println(spinner.selectedItem)



            when (spinner.selectedItem) {
                "PHONE" -> {
                    val phone = SmartPhone(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "PHONE",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString().toDouble(),
                        adicional3.text.toString(),
                        adicional4.text.toString().toInt()

                    )
                    val email = user.email
                    service.createPhone(phone, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "COMPUTER" -> {
                    val computer = Computer(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "COMPUTER",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString(),
                        adicional3.text.toString().toInt(),
                        adicional4.text.toString().toInt()
                    )
                    val email = user.email
                    service.createComputer(computer, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "HOUSEHOLD" -> {
                    val household = HouseHold(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "HOUSEHOLD",
                        0,
                        adicional1.text.toString().toDouble(),
                        adicional2.text.toString().toInt(),
                    )

                    val email = user.email
                    service.createHousehold(household, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "FASHIONTOP" -> {
                    val fashionTop = FashionTop(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "FASHIONTOP",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString().toInt(),
                    )
                    val email = user.email
                    service.createFashionTop(fashionTop, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "FASHIONBOTTOM" -> {
                    val fashionBot = FashionBot(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "FASHIONBOT",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString().toInt(),
                    )
                    val email = user.email
                    service.createFashioBot(fashionBot, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "FOOTWEAR" -> {
                    val footwear = FootWear(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "FOOTWEAR",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString().toInt(),
                    )
                    val email = user.email
                    service.createFootwear(footwear, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "DRINK" -> {
                    val drink = Drink(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "DRINK",
                        0,
                        0,
                        "",
                        adicional1.text.toString().toInt(),
                        adicional2.text.toString(),
                        adicional3.text.toString().toInt(),
                        adicional4.text.toString(),

                        )
                    val email = user.email
                    service.createDrink(drink, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "FISH" -> {
                    val fish = Fish(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "FISH",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString().toInt(),
                        adicional3.text.toString(),
                        adicional4.text.toString().toInt(),
                        adicional5.text.toString()
                    )
                    val email = user.email
                    service.createFish(fish, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "MEAT" -> {
                    val meat = Meat(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "MEAT",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString().toInt(),
                        adicional3.text.toString(),
                        adicional4.text.toString().toInt(),
                        adicional5.text.toString()
                    )
                    val email = user.email
                    service.createMeat(meat, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "VEGETABLE" -> {
                    val vegetable = Vegetable(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "VEGETABLE",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString(),
                        calories = 0,
                        expiringDate = "",
                        adicional3.text.toString().toInt(),
                        adicional4.text.toString(),

                        )
                    val email = user.email
                    service.createVegetable(vegetable, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }

                "FRUIT" -> {
                    val fruit = Fruit(
                        0,
                        name.text.toString(),
                        price.text.toString().toDouble(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        seller.text.toString(),
                        1,
                        0,
                        material.text.toString(),
                        brand.text.toString(),
                        "VEGETABLE",
                        0,
                        "",
                        adicional1.text.toString().toInt(),
                        adicional2.text.toString(),
                        adicional3.text.toString().toInt(),
                        adicional4.text.toString()
                    )

                    val email = user.email
                    service.createFruit(fruit, email)
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                }
            }
        }


    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.selectedItem
        val adicional1 = findViewById<EditText>(R.id.editText1)
        val adicional2 = findViewById<EditText>(R.id.editText2)
        val adicional3 = findViewById<EditText>(R.id.editText3)
        val adicional4 = findViewById<EditText>(R.id.editText4)
        val adicional5 = findViewById<EditText>(R.id.editText5)

        when (selectedItem) {
            "PHONE" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.GONE
                adicional1.setHint("Display")
                adicional2.setHint("Size")
                adicional3.setHint("Processor")
                adicional4.setHint("Guarantee")
            }

            "COMPUTER" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.GONE
                adicional1.setHint("Operating System")
                adicional2.setHint("Storage")
                adicional3.setHint("RAM")
                adicional4.setHint("Guarantee")
            }

            "HOUSEHOLD" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.GONE
                adicional5.visibility = View.GONE
                adicional1.setHint("Power Consumption")
                adicional2.setHint("Noise Level")
                adicional3.setHint("Guarantee")
            }

            "FASHIONTOP" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.GONE
                adicional4.visibility = View.GONE
                adicional5.visibility = View.GONE
                adicional1.setHint("Type")
                adicional2.setHint("Size")
            }

            "FASHIONBOTTOM" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.GONE
                adicional4.visibility = View.GONE
                adicional5.visibility = View.GONE
                adicional1.setHint("Type")
                adicional2.setHint("Size")
            }

            "FOOTWEAR" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.GONE
                adicional4.visibility = View.GONE
                adicional5.visibility = View.GONE
                adicional1.setHint("Type")
                adicional2.setHint("Size")
            }

            "DRINK" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.VISIBLE
                adicional1.setHint("Type")
                adicional2.setHint("Calories")
                adicional3.setHint("Expiration Date")
                adicional4.setHint("Units")
                adicional5.setHint("Quantity")
            }

            "FISH" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.VISIBLE
                adicional1.setHint("Calories")
                adicional2.setHint("Fishing Method")
                adicional3.setHint("Expiration Date")
                adicional4.setHint("Units")
                adicional5.setHint("Quantity")
            }

            "MEAT" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.VISIBLE
                adicional1.setHint("Origin")
                adicional2.setHint("Units")
                adicional3.setHint("Quantity")
            }

            "VEGETABLE" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.GONE
                adicional1.setHint("Origin")
                adicional2.setHint("Season")
                adicional3.setHint("Units")
                adicional4.setHint("Quantity")
            }

            "FRUIT" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.GONE
                adicional1.setHint("Calories")
                adicional2.setHint("Expiration Date")
                adicional3.setHint("Units")
                adicional4.setHint("Quantity")
            }

            "None" -> {
                adicional1.visibility = View.GONE
                adicional2.visibility = View.GONE
                adicional3.visibility = View.GONE
                adicional4.visibility = View.GONE
                adicional5.visibility = View.GONE
            }


        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }
}