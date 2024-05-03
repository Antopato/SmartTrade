package com.example.smarttrade

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import com.example.smarttrade.classes.typeofusers.Merchant
import java.io.ByteArrayOutputStream
import java.io.File


class AddProductActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {


    lateinit var updateImage : ImageView

    val service = BusinessLogic()
    val callsService = HTTPcalls()

    fun saveImage(): ByteArray {
        val bitmap = (updateImage.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }



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
        val user = intent.getSerializableExtra("user") as Merchant

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
        additionalInfo.visibility = View.GONE

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

        var image : ByteArray = byteArrayOf()

        updateImage.setOnClickListener() {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            image = saveImage()
        }


       spinner.onItemSelectedListener = this


        addProductbutt.setOnClickListener() {
            println(spinner.selectedItem)
            val seller = Sell(0, 0, user.email ,1, price.text.toString().toDouble())


            when (spinner.selectedItem) {
                "PHONE" -> {
                    val phone = SmartPhone(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "PHONE",
                        adicional1.text.toString(),
                        adicional2.text.toString().toDouble(),
                        adicional3.text.toString(),
                        adicional4.text.toString().toInt()

                    )
                    service.createSmartphone(phone, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "COMPUTER" -> {
                    val computer = Computer(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "COMPUTER",
                        adicional1.text.toString(),
                        adicional2.text.toString(),
                        adicional3.text.toString().toInt(),
                        adicional4.text.toString().toInt()
                    )
                    val email = user.email
                    service.createComputer(computer, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "HOUSEHOLD" -> {
                    val household = HouseHold(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "HOUSEHOLD",
                        adicional1.text.toString().toInt(),
                        adicional2.text.toString().toDouble(),
                        adicional3.text.toString().toInt(),
                    )

                    val email = user.email
                    service.createHousehold(household, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "FASHIONTOP" -> {
                    val fashionTop = FashionTop(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "FASHIONTOP",
                        adicional1.text.toString(),
                        adicional2.text.toString(),
                    )
                    val email = user.email
                    service.createFashionTop(fashionTop, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "FASHIONBOTTOM" -> {
                    val fashionBot = FashionBot(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "FASHIONBOT",
                        adicional1.text.toString(),
                        adicional2.text.toString()
                    )
                    val email = user.email
                    service.createFashionBottom(fashionBot, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "FOOTWEAR" -> {
                    val footwear = FootWear(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "FOOTWEAR",
                        adicional1.text.toString(),
                        adicional2.text.toString(),
                    )
                    val email = user.email
                    service.createFootWear(footwear, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "DRINK" -> {
                    val drink = Drink(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "DRINK",
                        0,
                        adicional1.text.toString(),
                        adicional2.text.toString().toInt(),
                        adicional3.text.toString(),
                        adicional5.text.toString().toInt(),
                        adicional4.text.toString()
                        )
                    val email = user.email
                    service.createDrink(drink, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "FISH" -> {
                    val fish = Fish(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "FISH",
                        adicional1.text.toString(),
                        adicional2.text.toString().toInt(),
                        adicional3.text.toString(),
                        adicional5.text.toString().toInt(),
                        adicional4.text.toString()
                    )
                    val email = user.email
                    service.createFish(fish, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "MEAT" -> {
                    val meat = Meat(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "MEAT",
                        adicional1.text.toString(),
                        100,
                        "2024-05-18",
                        adicional3.text.toString().toInt(),
                        adicional2.text.toString()
                    )
                    val email = user.email
                    service.createMeat(meat, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "VEGETABLE" -> {
                    val vegetable = Vegetable(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "VEGETABLE",
                        adicional1.text.toString(),
                        adicional2.text.toString(),
                        calories = 0,
                        expiringDate = "",
                        adicional4.text.toString().toInt(),
                        adicional3.text.toString()
                    )
                    val email = user.email
                    service.createVegetable(vegetable,seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }

                "FRUIT" -> {
                    val fruit = Fruit(
                        0,
                        name.text.toString(),
                        description.text.toString(),
                        production.text.toString(),
                        additionalInfo.text.toString(),
                        "imagen",
                        1,
                        material.text.toString(),
                        brand.text.toString(),
                        "VEGETABLE",
                        "",
                        adicional1.text.toString().toInt(),
                        adicional2.text.toString(),
                        adicional4.text.toString().toInt(),
                        adicional3.text.toString()
                    )

                    val email = user.email
                    service.createFruit(fruit, seller, image)
                    val intent = Intent(this, CatalogActivity::class.java)
                    intent.putExtra("user", user)
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
                adicional1.setHint("Fishing Method")
                adicional2.setHint("Calories")
                adicional3.setHint("Expiration Date")
                adicional4.setHint("Units")
                adicional5.setHint("Quantity")
            }

            "MEAT" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.GONE
                adicional5.visibility = View.GONE
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