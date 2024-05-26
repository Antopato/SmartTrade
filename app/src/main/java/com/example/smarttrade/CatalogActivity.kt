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
import com.example.smarttrade.classes.typeofusers.Merchant
import com.example.smarttrade.databinding.CatalogPageBinding
import org.json.JSONObject

class CatalogActivity : AppCompatActivity() {

    var maxPrice = 0
    var minPrice = 0
    val service = BusinessLogic()
    var list = emptyList<Product?>()


    lateinit var binding : CatalogPageBinding
    @SuppressLint("MissingInflatedId", "CutPasteId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = CatalogPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = intent.getSerializableExtra("user") as User?
        val buttonCertificate = binding.buttonCertificate
        val buttonAddProduct = binding.buttonAddProduct


        if(user?.type == "CLIENT"){
            buttonCertificate.visibility = View.INVISIBLE
            buttonAddProduct.visibility = View.INVISIBLE
            binding.buttonMyOrders.visibility = View.VISIBLE
            list = service.getProducts()
        }else if(user?.type == "ADMIN"){
            buttonAddProduct.visibility = View.INVISIBLE
            binding.buttonMyOrders.visibility = View.INVISIBLE
            list = service.getProducts()
        }else{
            buttonCertificate.visibility = View.INVISIBLE
            list = service.getMerchantProductsById(user!!.email)
            println("Seteada la lista de merchant")
            binding.textViewCatalog.text = "My Products"
        }

        binding.imageViewProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        val recyclerList = mutableListOf<Product?>()
        recyclerList.addAll(list)

        val recycler = binding.recyclerView
        val seekbar = binding.seekBar
        val seekValue = binding.seekvalue
        val searchView = binding.searchText


        val adapter = ProductsAdapter(this,recyclerList,user)
        recycler.adapter= adapter
        recycler.setLayoutManager(LinearLayoutManager(this))

        val filterLayout = binding.filterLayout
        filterLayout.visibility = View.INVISIBLE

        bindSpinnData(binding.tag1)
        bindSpinnData(binding.tag2)
        bindSpinnData(binding.tag3)

        searchView.setOnKeyListener { v, keyCode, event ->
            println(keyCode)
            if(keyCode== KeyEvent.KEYCODE_ENTER){
                var filter = searchView.text
                var filterList = mutableListOf<Product>()
                for (product in list){
                    if(product!!.name.contains(filter)){
                        filterList.add(product)
                        println(product.name)
                    }
                }
                recyclerList.clear()
                recyclerList.addAll(filterList)
                adapter!!.notifyDataSetChanged()

                return@setOnKeyListener true
            }else{
                return@setOnKeyListener false
            }
        }

        binding.imageCar.setOnClickListener{
            if(user.type=="CLIENT"){
                val intent = Intent(this, ShoppingCarActivity::class.java)
                intent.putExtra("user",user)
                startActivity(intent)
            }else{
                val intent = Intent(this, MerchantCertificatesActivity::class.java)
                intent.putExtra("user",user)
                startActivity(intent)
            }
        }
        buttonAddProduct.setOnClickListener {
            val intent = Intent(this, CopyProductActivity::class.java)
            intent.putExtra("user",  user)
            startActivity(intent)
        }

        buttonCertificate.setOnClickListener {
            val intent = Intent(this, CertificateRequestsActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        binding.imageViewLogOut.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekValue.text = "$progress€"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.minimumSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.minimumValue.text = "$progress€"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        binding.buttonFiler.setOnClickListener(){
            changeVisibilityFilter()
        }

        binding.applyFilter.setOnClickListener(){
            val priceList = mutableListOf<Product>()
            val json = adapter!!.json
            val minimumlist = setMinimumValue(priceList, json)
            val maximumlist = setMaximumValue(minimumlist, json)
            val finalList = setCategoryFilter(maximumlist)

            recyclerList.clear()
            recyclerList.addAll(finalList)
            adapter!!.notifyDataSetChanged()
        }

        if(user?.type == "CLIENT"){
            binding.buttonMyOrders.setOnClickListener {
                val intent = Intent(this, MyOrdersActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
        } else {
            binding.buttonMyOrders.setOnClickListener {
                val intent = Intent(this, MerchantOrdersActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
        }






    }
    private fun setMinimumValue(pricelist : MutableList<Product>, json : JSONObject): MutableList<Product>{
        val string = binding.minimumValue.text
        val number = string.dropLast(1).toString().toInt()
        minPrice=number

        for(product in list) {
            val price = json.get(product!!.productId.toString()) as Double
            println(product!!.name + " " + price)
            if (price >= minPrice) {
                println(product.name + " cuesta más de " + minPrice)
                pricelist.add(product)
            }
        }
        return pricelist
    }

    private fun setMaximumValue(filterlist : MutableList<Product>, json : JSONObject) : MutableList<Product>{
        val string = binding.seekvalue.text
        val number = string.dropLast(1).toString().toInt()
        val maxList = mutableListOf<Product>()
        if(number!=0) {maxPrice=number}
        else{maxPrice=100000000}

        for(product in filterlist){
            val price = json.get(product!!.productId.toString()) as Double
            println(product.name + " " + price)
            if(price<=maxPrice) {
                println(product.name + " cuesta menos de " + minPrice)

                maxList.add(product)
            }
        }
        return maxList
    }
    private fun changeVisibilityFilter(){
        if(binding.filterLayout.visibility == View.INVISIBLE){
            binding.filterLayout.visibility = View.VISIBLE
        }else{
            binding.filterLayout.visibility =View.INVISIBLE
        }
    }
    private fun setCategoryFilter(filterlist : MutableList<Product>) : MutableList<Product>{

        val cat1 =binding.tag1.selectedItem
        val cat2 =binding.tag2.selectedItem
        val cat3 =binding.tag3.selectedItem

        val categoryList = mutableListOf<Product>()

        for(product in filterlist){
            val cond1 = cat1=="None" || cat1==product.productType
            val cond2 = cat2=="None" || cat2==product.productType
            val cond3 = cat3=="None" || cat3==product.productType

            if(cond1 && cond2 && cond3){
                categoryList.add(product)
            }else if(cat1==product.productType ||
                cat2==product.productType ||
                cat3==product.productType ){
                categoryList.add(product)
            }
        }
        return categoryList
    }


    private fun bindSpinnData(spinner : Spinner){
        val options = resources.getStringArray(R.array.options)
        val adapterSpinn = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinner.adapter = adapterSpinn

    }
}