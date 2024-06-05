package com.example.smarttrade
import com.example.smarttrade.classes.ShoppingCart
//import com.example.smarttrade.Memento.MementoClass

class CareTaker(private val originator: Originator) {
    private val mementos = mutableListOf<Originator.Memento>()

    fun getMemento(index: Int): Originator.Memento? {
        return if (index in mementos.indices) mementos[index] else null
    }

    fun addMemento(m: Originator.Memento) {
        println("ha habido un cambio")
        mementos.add(m)
    }

    fun undo() {
        if (mementos.isNotEmpty()) {
            println("longitud de mementos: ${mementos.size}")
            val lastMemento = mementos.removeAt(mementos.size - 1)
            originator.restaurar(lastMemento)
        }
    }

    fun getLasMemento(): List<ShoppingCart>{
        println("longitud de mementos: ${mementos.size}")
        return mementos.get(mementos.size - 1).getState()
    }

    fun clear() {
        mementos.clear()
    }
}
