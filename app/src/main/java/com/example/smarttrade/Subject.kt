package com.example.smarttrade

interface Subject {
    var listObservers : MutableList<Observer>
    fun notifyObservers()

    fun addObserver(observer : Observer)

    fun removeObserver(observer : Observer)
}