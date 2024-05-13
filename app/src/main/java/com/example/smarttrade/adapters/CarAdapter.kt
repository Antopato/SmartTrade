package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.ShoppingCarActivity
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.classes.User

class CarAdapter(val context: Context, val list: MutableList<ShoppingCart>, val observer : ShoppingCarActivity, val user : User) : RecyclerView.Adapter<CarAdapter.MyViewHolder>() {
    var sum = 0
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =  inflater.inflate(R.layout.shopping_car_row, parent, false)
        val viewHold = MyViewHolder(view,context,list)

        return viewHold
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = service.getProductById(list.get(position).product_id)
        val userId = list.get(position).shopping_cart_owner
        holder.price.setText(list.get(position).price.toString() + "€")
        println(list.get(position).quantity.toString())
        holder.amount.setText(list.get(position).quantity.toString())
        holder.name.setText(product.name)

        val unitPrice = list.get(position).price
        val amount = list.get(position).quantity
        sum += unitPrice*amount
        observer.change()
        println(sum)

        val image = service.getImageByType(product.productType, product.productId)
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))

        holder.minusButton.setOnClickListener(){
            val number = holder.amount.text.toString().toInt()
            if(number-1 >0 ){
                holder.amount.text = (number-1).toString()
                list.get(position).quantity = (number-1)
                var value = list.get(position).price
                sum -= value
                observer.change()
            }
        }

        holder.plusButton.setOnClickListener(){
            val number = holder.amount.text.toString().toInt() +1
            list.get(position).quantity = number
            holder.amount.text = number.toString()
            var value = list.get(position).price
            sum+= value
            observer.change()
        }
        holder.laterButton.setOnClickListener(){
            service.addToLaterList(product.productId,userId)
            println("Añadido a forLater")
            service.deleteProdFromCart(product.productId,userId)
            println("Producto eliminado")
            sum=0
            observer.changeData(true, position)
        }
        holder.deleteButton.setOnClickListener(){
            service.deleteProdFromCart(product.productId,userId)
            sum=0
            observer.changeData(false, position)
        }

    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun getTotal() : Int
    {
        println("He devuelto sum con un valor de "+ sum)
        return sum
    }



    class MyViewHolder(itemView: View, context: Context, list:List<ShoppingCart>) : RecyclerView.ViewHolder(itemView){
        val price : TextView = itemView.findViewById(R.id.priceText)
        val name : TextView = itemView.findViewById(R.id.listProductName)
        val plusButton : Button = itemView.findViewById(R.id.plusButton)
        val minusButton : Button = itemView.findViewById(R.id.minusButton)
        val amount : TextView = itemView.findViewById(R.id.amountText)
        val deleteButton : Button = itemView.findViewById(R.id.listDeleteButton)
        val laterButton : Button = itemView.findViewById(R.id.forLaterButton)
        val image : ImageView = itemView.findViewById(R.id.listProductImage)
        init{
            name.setOnClickListener {

                val intent = Intent(context, ProductActivity::class.java)
                //intent.putExtra("product", list.get(adapterPosition))
                //val bitmap = (image.drawable).toBitmap()
                //val stream = ByteArrayOutputStream()
                //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                //val byteArray = stream.toByteArray()
                //intent.putExtra("image", byteArray)
                context.startActivity(intent)


            }
        }




    }

}