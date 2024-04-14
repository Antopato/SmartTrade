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
import com.example.smarttrade.classes.User

class AddProductActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {


    lateinit var updateImage : ImageView


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

        val spinner : Spinner = findViewById(R.id.spinnerCategories)
        val options = resources.getStringArray(R.array.options)

        val name = findViewById<EditText>(R.id.editTextName)
        val price = findViewById<EditText>(R.id.editTextPrice)
        val company = findViewById<EditText>(R.id.editTextCompany)
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

        updateImage.setOnClickListener(){
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        spinner.onItemSelectedListener = this

        addProductbutt.setOnClickListener(){
            println(spinner.selectedItem)

            val nameText = name.text
            val priceText = price.text
            val companyText = company.text
            val brandText = brand.text
            val descriptionText = description.text
            val materialText = material.text
            val productionText = production.text
            val additionalInfoText = additionalInfo.text

            when(spinner.selectedItem){
                "PHONE" -> {
                    val display = adicional1.text
                    val size = adicional2.text
                    val processor = adicional3.text
                    val guarantee = adicional4.text

                    //val phone : Json

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

        when(selectedItem) {
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
                adicional2.setHint("RAM")
                adicional3.setHint("Storage")
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