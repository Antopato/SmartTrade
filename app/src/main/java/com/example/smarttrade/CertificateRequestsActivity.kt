package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.CertificatesAdapter
import com.example.smarttrade.classes.User

class CertificateRequestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val service = BusinessLogic()
        setContentView(R.layout.certificate_requests)

        val user = intent.getSerializableExtra("user") as User

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val listUncertified = service.getUncertifiedCertificates()
        val listOfProducts = service.getProducts()

        println(listUncertified)
        println(listOfProducts)


        val adapter = CertificatesAdapter(this, listUncertified,user)
        recyclerView.adapter = adapter
        recyclerView.setLayoutManager(LinearLayoutManager(this))
    }
}