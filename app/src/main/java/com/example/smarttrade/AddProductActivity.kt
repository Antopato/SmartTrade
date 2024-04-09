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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.result.contract.ActivityResultContracts.*

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        updateImage = findViewById<ImageView>(R.id.uploadImage)

        val spinner : Spinner = findViewById(R.id.spinnerCategories)
        val options = resources.getStringArray(R.array.options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinner.adapter = adapter

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }

        updateImage.setOnClickListener(){
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        spinner.onItemClickListener = this

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position).toString()
        val adicional1 = findViewById<EditText>(R.id.editText1)
        val adicional2 = findViewById<EditText>(R.id.editText2)
        val adicional3 = findViewById<EditText>(R.id.editText3)
        val adicional4 = findViewById<EditText>(R.id.editText4)
        val adicional5 = findViewById<EditText>(R.id.editText5)

        when(selectedItem) {
            "SmartPhone" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional1.setText("Display")
                adicional2.setText("Size")
                adicional3.setText("Processor")
                adicional4.setText("Guarantee")
            }
            "Computer" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional1.setText("Operating System")
                adicional2.setText("RAM")
                adicional3.setText("Storage")
                adicional4.setText("Guarantee")
            }
            "HouseHold" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional1.setText("Power Consumption")
                adicional2.setText("Noise Level")
                adicional3.setText("Guarantee")
            }
            "Fashion Top" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional1.setText("Type")
                adicional2.setText("Size")
            }
            "Fashion Bottom" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional1.setText("Type")
                adicional2.setText("Size")
            }
            "Foot Wear" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional1.setText("Type")
                adicional2.setText("Size")
            }
            "Drinks" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.VISIBLE
                adicional1.setText("Type")
                adicional2.setText("Calories")
                adicional3.setText("Expiration Date")
                adicional4.setText("Units")
                adicional5.setText("Quantity")
            }
            "Fish" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional5.visibility = View.VISIBLE
                adicional1.setText("Calories")
                adicional2.setText("Fishing Method")
                adicional3.setText("Expiration Date")
                adicional4.setText("Units")
                adicional5.setText("Quantity")
            }
            "Meat" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional1.setText("Origin")
                adicional2.setText("Units")
                adicional3.setText("Quantity")
            }
            "Vegetables" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional1.setText("Origin")
                adicional2.setText("Season")
                adicional3.setText("Units")
                adicional4.setText("Quantity")
            }
            "Fruits" -> {
                adicional1.visibility = View.VISIBLE
                adicional2.visibility = View.VISIBLE
                adicional3.visibility = View.VISIBLE
                adicional4.visibility = View.VISIBLE
                adicional1.setText("Calories")
                adicional2.setText("Expiration Date")
                adicional3.setText("Units")
                adicional4.setText("Quantity")
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