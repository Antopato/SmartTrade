package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BusinessLogic
import com.example.smarttrade.CertificateValidationActivity
import com.example.smarttrade.R
import com.example.smarttrade.classes.Product
import com.example.smarttrade.classes.User

class CertificatesAdapter(var context: Context, var list: List<Product?>, var user: User) : RecyclerView.Adapter<CertificatesAdapter.CertificateHolder>() {
    val service = BusinessLogic()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateHolder {
        val inflater : LayoutInflater  = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.recycler_row_certificate, parent, false)
        return CertificateHolder(view, context, list, user)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: CertificatesAdapter.CertificateHolder, position: Int) {
        holder.productName.setText(list.get(position)!!.name)
        holder.productCompany.setText(list.get(position)!!.seller)
        holder.productBrand.setText(list.get(position)!!.brand)
        val type = list.get(position)!!.productType
        val image = service.getImageByType(type, list[position]!!.productId)
        holder.productImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))
        //holder.productImage.setImageBitmap(list.get(position).Img)
    }

    class CertificateHolder(itemView: View, context: Context, list:List<Product?>, user : User) : RecyclerView.ViewHolder(itemView){
        var productImage : ImageView = itemView.findViewById(R.id.imageViewProductImage)
        var productName : TextView = itemView.findViewById(R.id.textViewProductName)
        var productCompany : TextView = itemView.findViewById(R.id.textViewProductCompany)
        var productBrand : TextView = itemView.findViewById(R.id.textViewProductBrand)

        init{
            itemView.setOnClickListener {
                val intent = Intent(context, CertificateValidationActivity::class.java)
                intent.putExtra("product", list.get(adapterPosition))
                intent.putExtra("user", user)
                //intent.putExtra("name", list.get(adapterPosition).Name)
                //intent.putExtra("company", list.get(adapterPosition).Company)
                //intent.putExtra("brand", list.get(adapterPosition).Brand)
                //intent.putExtra("image", list.get(adapterPosition).Img.toString())
                //faltan materials, production y aditional information

                context.startActivity(intent)
            }
        }
    }
}