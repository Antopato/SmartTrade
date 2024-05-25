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
import com.example.smarttrade.classes.CreditCard

class CreditCardAdapter(var list : List<CreditCard>, var context : Context) : RecyclerView.Adapter<CreditCardAdapter.MyViewHolder>() {
    var selection = false
    lateinit var selectedCreditCard : CreditCard
    val listHolders = mutableListOf<MyViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =  inflater.inflate(R.layout.select_credit_card_row, parent, false)
        val viewHold = CreditCardAdapter.MyViewHolder(view, this, list)

        listHolders.add(viewHold)

        return viewHold
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.number.text = list.get(position).cardNumber
        holder.expirationDate.text = list.get(position).expirationDate
        holder.CVV.text = list.get(position).CVV.toString()
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

    class MyViewHolder(itemView :View, var adapter : CreditCardAdapter, var list : List<CreditCard>) : RecyclerView.ViewHolder(itemView), Subject, Observer {
        override var listObservers = mutableListOf<Observer>()
        var selected = false

        val number = itemView.findViewById<TextView>(R.id.textViewNumber)
        val expirationDate = itemView.findViewById<TextView>(R.id.textViewExpirationDate)
        val CVV = itemView.findViewById<TextView>(R.id.textViewCVV)
        val cardView = itemView.findViewById<CardView>(R.id.cardView)
        val layout = itemView.findViewById<ConstraintLayout>(R.id.creditCardRow)

        init{
            layout.setOnClickListener(){
                println("he encontrado el itemView")
                this.selectCreditCard()
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
        fun selectCreditCard(){
            if (!selected) {
                notifyObservers()
                println("observers notificados")
                cardView.setBackgroundColor(Color.parseColor("#8BD1EF"))
                adapter.selection= true
                selected = true
                adapter.selectedCreditCard = list.get(adapterPosition)

            }else{
                cardView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                selected = false
                adapter.selection = false

            }
        }
    }
}