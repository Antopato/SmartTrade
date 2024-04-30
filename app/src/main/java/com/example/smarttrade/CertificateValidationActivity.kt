package com.example.smarttrade

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User

class CertificateValidationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val service = BusinessLogic()
        super.onCreate(savedInstanceState)
        val product = intent.getSerializableExtra("product") as Product?
        val user = intent.getSerializableExtra("user") as User
        val byteArray = intent.getByteArrayExtra("image")
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        enableEdgeToEdge()
        setContentView(R.layout.certificate_validation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            intent = Intent(this, CertificateRequestsActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        val buttonAccept = findViewById<Button>(R.id.buttonAccept)
        buttonAccept.setOnClickListener {
            service.validateCertificate(product,user)
        }
        val buttonDecline = findViewById<Button>(R.id.buttonDecline)
        buttonDecline.setOnClickListener {
            service.declineCertificate(product,user)
        }
        //val productName = intent.getStringExtra("name")
        //val productCompany = intent.getStringExtra("company")
        //val productBrand = intent.getStringExtra("brand")
        //val productImage = intent.getStringExtra("image")

        val textViewProductName = findViewById<TextView>(R.id.textViewProductName)
        val textViewProductCompany = findViewById<TextView>(R.id.textViewCompany)
        val textViewProductBrand = findViewById<TextView>(R.id.textViewBrand)
        val textViewProductProduction = findViewById<TextView>(R.id.textViewProduction)
        val textViewProductMaterial = findViewById<TextView>(R.id.textViewMaterials)
        val textViewProductAdition = findViewById<TextView>(R.id.textViewAditionalInformation)
        val imageViewProductImage = findViewById<ImageView>(R.id.imageViewProductImage)

        textViewProductName.text = product!!.name
        //textViewProductCompany.text = "Company " + product!!.seller
        textViewProductBrand.text = "Brand: " + product!!.brand
        textViewProductProduction.text = "Production: "+ product!!.production
        textViewProductMaterial.text = "Materials "+ product!!.materials
        textViewProductAdition.text= "Aditional information "+product!!.additionalInfo
        imageViewProductImage.setImageBitmap(bitmap)
    }
}