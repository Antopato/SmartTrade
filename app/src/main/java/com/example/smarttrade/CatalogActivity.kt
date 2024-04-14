package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.ProductsAdapter
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User

class CatalogActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "CutPasteId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


        setContentView(R.layout.catalog_page)


        val user = intent.getSerializableExtra("user") as User?
        //println("Tipo en catalog "+user.type)
        val buttonCertificate = findViewById<Button>(R.id.buttonCertificate)
        val buttonAddProduct = findViewById<Button>(R.id.buttonAddProduct)


        /*if(user.type == "CLIENT"){
            buttonCertificate.visibility = View.INVISIBLE
            buttonAddProduct.visibility = View.INVISIBLE
        }else if(user.type == "ADMIN"){
            buttonAddProduct.visibility = View.INVISIBLE
        }else{
            buttonCertificate.visibility = View.INVISIBLE
        }*/

        val service = BusinessLogic()
        val list = service.getProducts()
        val listOfImages: MutableList<String?> = mutableListOf()

        for(product in list){
            listOfImages.add(product.image)
        }


        var maxPrice : Int
        var categoryFilter : List<String>

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val seekbar = findViewById<SeekBar>(R.id.seekBar)
        val seekValue = findViewById<TextView>(R.id.seekvalue)
        val searchView = findViewById<EditText>(R.id.searchText)



        val adapter = ProductsAdapter(this,list,user,listOfImages)
        recycler.adapter= adapter
        recycler.setLayoutManager(LinearLayoutManager(this))

        val filterLayout = findViewById<ConstraintLayout>(R.id.filterLayout)
        filterLayout.visibility = View.INVISIBLE

        val tag1 = findViewById<Spinner>(R.id.tag1)
        val tag2 = findViewById<Spinner>(R.id.tag2)
        val tag3 = findViewById<Spinner>(R.id.tag3)
        bindSpinnData(tag1)
        bindSpinnData(tag2)
        bindSpinnData(tag3)

        searchView.setOnKeyListener { v, keyCode, event ->
            println(keyCode)
            if(keyCode== KeyEvent.KEYCODE_ENTER){
                println("He entrado")
                var filter = searchView.text
                var filterList = mutableListOf<Product>()
                for (product in list){
                    if(product.name.contains(filter)){
                        filterList.add(product)
                        println(product.name)
                    }
                }
                val adapterFilter = ProductsAdapter(this,list,user,listOfImages)
                recycler.adapter = adapterFilter
                println("He cambiado el adapter")
                adapterFilter.notifyDataSetChanged()
                recycler.adapter = adapterFilter
                recycler.setLayoutManager(LinearLayoutManager(this))


                return@setOnKeyListener true
            }else{
                return@setOnKeyListener false
            }
        }

        buttonAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("user", user)

            startActivity(intent)
        }

        buttonCertificate.setOnClickListener {
            val intent = Intent(this, CertificateRequestsActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekValue.text = "$progress€"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val filterbutt = findViewById<Button>(R.id.buttonFiler)

        filterbutt.setOnClickListener(){
            if(filterLayout.visibility == View.INVISIBLE){
                filterLayout.visibility = View.VISIBLE
            }else{
                filterLayout.visibility =View.INVISIBLE
            }

        }

        val applyFilter = findViewById<Button>(R.id.apply_filter)
        applyFilter.setOnClickListener(){
            val string = seekValue.text
            val number = string.dropLast(1).toString().toInt()
            if(number!=0){
                maxPrice=number
            }
            /*
            tag1.selectedItem
            tag2.selectedItem
            tag3.selectedItem*/


        }




    }

    private fun bindSpinnData(spinner : Spinner){
        val options = resources.getStringArray(R.array.options)
        val adapterSpinn = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinner.adapter = adapterSpinn

    }


}