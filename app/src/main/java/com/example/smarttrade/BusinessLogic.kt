package com.example.smarttrade

class BusinessLogic {
    fun logIn(user:String, pass:String){
        if(user!=""){
            if(pass!=""){
                //Send(user,pass)
            }else{
                throw(Exception("The password section is void"))
            }

        }else{
            throw(Exception("The user section is void"))
        }
    }
}