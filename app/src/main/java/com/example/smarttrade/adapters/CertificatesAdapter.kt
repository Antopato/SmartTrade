package com.example.smarttrade.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.R
import com.example.smarttrade.classes.Certificate

class CertificatesAdapter(var context: Context, var list: List<Certificate>) : RecyclerView.Adapter<CertificatesAdapter.CertificateHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateHolder {
        var inflater : LayoutInflater  = LayoutInflater.from(context)
        var view : View = inflater.inflate(R.layout.recycler_row_certificate, parent, false)
        return CertificateHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: CertificatesAdapter.CertificateHolder, position: Int) {
        holder.productName.setText(list.get(position).Name)
        holder.productCompany.setText(list.get(position).Company)
        holder.productBrand.setText(list.get(position).Brand)
        holder.productImage.setImageBitmap(list.get(position).Img)
    }

    class CertificateHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var productImage : ImageView = itemView.findViewById(R.id.imageViewProductImage)
        var productName : TextView = itemView.findViewById(R.id.textViewProductName)
        var productCompany : TextView = itemView.findViewById(R.id.textViewProductCompany)
        var productBrand : TextView = itemView.findViewById(R.id.textViewProductBrand)
    }
}