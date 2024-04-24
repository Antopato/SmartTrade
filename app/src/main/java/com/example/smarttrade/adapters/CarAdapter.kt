package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.ShoppingCarActivity
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.Sell
import com.example.smarttrade.classes.User
import java.io.ByteArrayOutputStream

class CarAdapter(val context: Context, val list: MutableList<Sell>, val observer : ShoppingCarActivity) : RecyclerView.Adapter<CarAdapter.MyViewHolder>() {
    var sum = 0
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =  inflater.inflate(R.layout.shopping_car_row, parent, false)
        val viewHold = MyViewHolder(view,context,list)

        return viewHold
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.seller.setText(list.get(position)!!.seller)
        holder.name.setText(list.get(position)!!.name)
        holder.price.setText(list.get(position)!!.price + "€")
        holder.amount.setText(list.get(position)!!.amount)
        sum += list.get(position)!!.price.toInt()
        observer.change()
        println(sum )
        //val image = service.getImageByType(type, list[position]!!.productId)
        //println("he acabdo de buscar el tipo $type")
        //holder.image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))

        holder.minusButton.setOnClickListener(){
            val number = holder.amount.text.toString().toInt()
            if(number-1 >0 ){
                holder.amount.text = (number-1).toString()
                var value = list.get(position).price
                sum -= value.toInt()
                observer.change()
            }
        }
        holder.plusButton.setOnClickListener(){
            val number = holder.amount.text.toString().toInt() +1
            holder.amount.text = number.toString()
            var value = list.get(position).price
            sum+= value.toInt()
            observer.change()
        }
        holder.deleteButton.setOnClickListener(){
            sum=0
            observer.changeData(position)
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
    class MyViewHolder(itemView: View, context: Context, list:List<Sell?>) : RecyclerView.ViewHolder(itemView){
        val price : TextView = itemView.findViewById(R.id.priceText)
        val name : TextView = itemView.findViewById(R.id.productName)
        val seller : TextView = itemView.findViewById(R.id.sellerName);
        val plusButton : Button = itemView.findViewById(R.id.plusButton)
        val minusButton : Button = itemView.findViewById(R.id.minusButton)
        val amount : TextView = itemView.findViewById(R.id.amountText)
        val deleteButton : Button = itemView.findViewById(R.id.deleteButton)
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