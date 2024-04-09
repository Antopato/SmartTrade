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

class CertificateValidationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            startActivity(intent)
        }

        val productName = intent.getStringExtra("name")
        val productCompany = intent.getStringExtra("company")
        val productBrand = intent.getStringExtra("brand")
        //val productImage = intent.getStringExtra("image")

        val textViewProductName = findViewById<TextView>(R.id.textViewProductName)
        val textViewProductCompany = findViewById<TextView>(R.id.textViewCompany)
        val textViewProductBrand = findViewById<TextView>(R.id.textViewBrand)
        val imageViewProductImage = findViewById<ImageView>(R.id.imageViewProductImage)

        val bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lavadora)
        textViewProductName.text = productName
        textViewProductCompany.text = "Company: " + productCompany
        textViewProductBrand.text = "Brand: " + productBrand
        imageViewProductImage.setImageBitmap(bitmap)
    }
}