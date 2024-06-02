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
import com.example.smarttrade.classes.ShoppingCart
import com.example.smarttrade.classes.User
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.ProductActivity
import com.example.smarttrade.R
import com.example.smarttrade.ShoppingCarActivity

class CarAdapter(
    val context: Context,
    val list: MutableList<ShoppingCart>,
    val observer: ShoppingCarActivity,
    val user: User
) : RecyclerView.Adapter<CarAdapter.MyViewHolder>() {
    var sum = 0
    val service = BusinessLogic()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.shopping_car_row, parent, false)
        return MyViewHolder(view, context, list)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sell = service.getSellById(list[position].product_id)
        val product = service.getProductById(sell!!.id_product)
        val userId = list[position].shopping_cart_owner
        holder.price.text = "${list[position].price}€"
        holder.amount.text = list[position].quantity.toString()
        holder.name.text = product.name

        val unitPrice = list[position].price
        val amount = list[position].quantity
        sum += unitPrice * amount
        observer.change()
        service.setImage(holder.image, product)
        val image = service.getImageByType(product.productType, product.productId)
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))

        holder.minusButton.setOnClickListener {
            observer.saveState()
            val number = holder.amount.text.toString().toInt()
            if (number - 1 > 0) {
                holder.amount.text = (number - 1).toString()
                list[position].quantity = (number - 1)
                val shoppingCartId: Int = list[position].shoppingCart_id
                val value = list[position].price
                sum -= value
                observer.change()
                service.minusOne(shoppingCartId)
                notifyDataSetChanged()
            }
        }

        holder.plusButton.setOnClickListener {
            observer.saveState()
            val number = holder.amount.text.toString().toInt() + 1
            list[position].quantity = number
            val shoppingCartId: Int = list[position].shoppingCart_id
            holder.amount.text = number.toString()
            val value = list[position].price
            sum += value
            observer.change()
            service.plusOne(shoppingCartId)
            notifyDataSetChanged()
        }

        holder.laterButton.setOnClickListener {
            observer.saveState()
            val product = list[position]
            service.addToLaterList(product.product_id, userId)
            service.deleteProdFromCart(product.product_id, userId)
            observer.changeData(product, position,true)
        }

        holder.deleteButton.setOnClickListener {
            observer.saveState()
            service.deleteProdFromCart(product.productId, userId)
            sum = 0
            observer.changeData(list[position], position,false)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun getTotal(): Int {
        return sum
    }

    class MyViewHolder(itemView: View, context: Context, list: List<ShoppingCart>) : RecyclerView.ViewHolder(itemView) {
        val price: TextView = itemView.findViewById(R.id.priceText)
        val name: TextView = itemView.findViewById(R.id.listProductName)
        val plusButton: Button = itemView.findViewById(R.id.plusButton)
        val minusButton: Button = itemView.findViewById(R.id.minusButton)
        val amount: TextView = itemView.findViewById(R.id.amountText)
        val deleteButton: Button = itemView.findViewById(R.id.listDeleteButton)
        val laterButton: Button = itemView.findViewById(R.id.forLaterButton)
        val image: ImageView = itemView.findViewById(R.id.listProductImage)

        init {
            name.setOnClickListener {
                val intent = Intent(context, ProductActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}
