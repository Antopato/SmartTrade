package com.example.smarttrade
import com.example.smarttrade.classes.ShoppingCart

class Originator {
    private var shoppingCart = mutableListOf<ShoppingCart>()

    fun setState(state: List<ShoppingCart>) {
        shoppingCart = state.toMutableList()
    }

    fun getState(): List<ShoppingCart> {
        return shoppingCart
    }

    fun guardar(): Memento {
        return Memento(shoppingCart.toMutableList())
    }

    fun restaurar(m: Memento) {
        shoppingCart = m.getState().toMutableList()
    }

    // Clase interna Memento
    inner class Memento(private val shoppingCart: List<ShoppingCart>) {
        fun getState(): List<ShoppingCart> {
            return this.shoppingCart
        }
    }

    fun removeProduct(productId: Int) {
        shoppingCart.removeAll { it.product_id == productId }
    }

    fun updateProductQuantity(productId: Int, quantity: Int) {
        shoppingCart.find { it.product_id == productId }?.quantity = quantity
    }
}
