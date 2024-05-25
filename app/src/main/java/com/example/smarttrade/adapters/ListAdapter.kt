package com.example.smarttrade.adapters

import android.app.ListActivity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.ListsActivity
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.User
import java.io.ByteArrayOutputStream

class ListAdapter(var context : Context, val listProd : List<Product>?,val listSell : List<Sell>?, val type : String, val user : User, val activity : ListsActivity) : RecyclerView.Adapter<ListAdapter.ListHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.lists_page_row, parent, false)
        var viewHold = if(isWhisList(type)) {
            ListHolder(view, context, listProd,null, user)
        }else{
            ListHolder(view,context, null, listSell,user)
        }

        return viewHold
    }


    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        var list = if(isWhisList(type)){
            listProd
        }else{
            var sellProduct = mutableListOf<Product>()
            for(sell in listSell!!){
                sellProduct.add(service.getProductById(sell.id_product))
            }
            sellProduct
        }

        println(list!!.get(position).name)
        holder.name.text = list!!.get(position).name
        holder.description.text = list!!.get(position).description
        setImage(holder,list!!.get(position))

        holder.deleteButt.setOnClickListener(){
            if(isWhisList(type)){
                service.deleteProdFromWhislist(list.get(position).productId, user.email)
                activity.refreshList(position)
            }else{
                service.deleteProdFromLater(list.get(position).productId, user.email)
                activity.refreshList(position)
            }
        }

        holder.toCartButt.setOnClickListener(){
           service.addProductToCar(listSell!!.get(position), user.email)
            service.deleteProdFromLater(list!!.get(position).productId, user.email)
            activity.refreshList(position)


        }

        holder.layoutProd.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("product", list.get(position))
            intent.putExtra("user", user)

            val bitmap = (holder.image.drawable).toBitmap()
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            intent.putExtra("image", byteArray)

            context.startActivity(intent)
        }
    }
    fun isWhisList(type : String):Boolean{
        return type == "whislist"
    }

/*    fun addProdToCar(){
        var string : String
        try {
            service.addProductToCar(selectedProduct, user.email)
            string =  "Product added to Shopping Cart"

        }catch(e:Exception){
            string = "This produt has alreade been added"
        }
        val widthInPixels = 920
        val heightInPixels = 570
        val popupView = LayoutInflater.from(context).inflate(R.layout.popup_advert, null)
        val popupWindow = PopupWindow(popupView, widthInPixels, heightInPixels, true)
        val advert = popupView.findViewById<TextView>(R.id.advertText)
        val buttonAdd = popupView.findViewById<View>(R.id.buttonAdd)
        advert.text= string

        buttonAdd.setOnClickListener(){
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }
*/
    override fun getItemCount(): Int {
        if(isWhisList(type)){
            return listProd!!.size
        }else{
            return listSell!!.size
        }
    }

    fun setImage(holder : ListHolder, product : Product){
        val type = product.productType
        val image = service.getImageByType(type, product.productId)
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))
    }


    class ListHolder(var itemView: View, var context : Context, var listProd : List<Product>?,var listSell: List<Sell>?, val user : User) :  RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.listProductName)
        val description = itemView.findViewById<TextView>(R.id.listProductDescription)
        val image = itemView.findViewById<ImageView>(R.id.listProductImage)
        val deleteButt = itemView.findViewById<Button>(R.id.listDeleteButton)
        val layoutProd = itemView.findViewById<ConstraintLayout>(R.id.listProductLayout)
        val toCartButt = itemView.findViewById<Button>(R.id.backToCartButt)

    }
}