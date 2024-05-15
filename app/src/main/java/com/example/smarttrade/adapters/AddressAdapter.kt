package com.example.smarttrade.adapters;

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.Observer
import com.example.smarttrade.R
import com.example.smarttrade.Subject
import com.example.smarttrade.classes.Address

class AddressAdapter(var list : List<Address>, var context : Context) : RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {
    var selection = false
    lateinit var selectedAddress : Address
    val listHolders = mutableListOf<MyViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =  inflater.inflate(R.layout.select_address_row, parent, false)
        val viewHold = AddressAdapter.MyViewHolder(view, this, list)

        listHolders.add(viewHold)

        return viewHold
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.street.text = list.get(position).street
        holder.city.text = list.get(position).city
        holder.province.text = list.get(position).province

    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun addAll(){
        println("Has llamado a AddAll "+ listHolders.count())
        for(holder in listHolders){
            for(observer in listHolders){
                if(holder!=observer){
                    holder.addObserver(observer)
                }
                println("Obserber y holder")
            }
        }
    }

    class MyViewHolder(itemView :View, var adapter : AddressAdapter,var  list : List<Address>, ) : RecyclerView.ViewHolder(itemView), Subject, Observer {
        override var listObservers = mutableListOf<Observer>()
        var selected = false

        val street = itemView.findViewById<TextView>(R.id.streetTextViewRow)
        val city = itemView.findViewById<TextView>(R.id.cityTextView)
        val province = itemView.findViewById<TextView>(R.id.provinceTextView)
        val cardView = itemView.findViewById<CardView>(R.id.cardView)
        val layout = itemView.findViewById<ConstraintLayout>(R.id.layoutInCar)

        init{
            layout.setOnClickListener(){
                println("he encontrado el itemView")
                this.selectAddress()
                adapter.addAll()
            }
        }
        override fun notifyObservers() {
            for(observer in listObservers){
                observer.update()
            }
        }

        override fun addObserver(observer: Observer) {
            listObservers.add(observer)
        }

        override fun removeObserver(observer: Observer) {
            listObservers.add(observer)
        }

        override fun update() {
            println("Cambio de color")
            this.cardView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            this.selected = false
        }
        fun selectAddress(){
            if (!selected) {
                notifyObservers()
                println("observers notificados")
                cardView.setBackgroundColor(Color.parseColor("#8BD1EF"))
                adapter.selection= true
                selected = true
                adapter.selectedAddress = list.get(adapterPosition)

            }else{
                cardView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                selected = false
                adapter.selection = false

            }
        }
    }
}
