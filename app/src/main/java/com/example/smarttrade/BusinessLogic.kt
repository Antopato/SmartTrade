package com.example.smarttrade

class BusinessLogic {
    fun logIn(user:String, pass:String): Boolean {
        if(user!=""){
            if(pass!=""){
                //Send(user,pass)
                return true
            }else{
                throw(Exception("The password section is void"))
            }

        }else{
            throw(Exception("The user section is void"))
        }
    }

    fun registerCompany(name:String, surname:String, email:String, pass:String,
                        holder:String, iban:String, date:String) {

    }
}