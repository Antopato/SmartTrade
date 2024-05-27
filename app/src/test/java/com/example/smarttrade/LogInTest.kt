package com.example.smarttrade

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LogInTest {
    val service = BusinessLogic()
    @Test
    fun logInCorrect(){
        val result = service.logIn("julian@gmail.com","123456")
        assertEquals("julian@gmail.com",result!!.email)
    }

    @Test
    fun voidPassword(){
        val exception = assertThrows(Exception::class.java) {
            service.logIn("julian@gmail.com","")
        }
        assertEquals("The password section is void", exception.message)
    }

    @Test
    fun voidEmail(){
        val exception = assertThrows(Exception::class.java){
            service.logIn("","123456")
        }
        assertEquals("The user section is void", exception.message)
    }

    @Test
    fun invalidPassword(){
        val exception = assertThrows(Exception::class.java){
            service.logIn("julian@gmail.com","12")
        }
        assertEquals("Incorrect password", exception.message)
    }

    @Test
    fun invalidEmail(){
        val exception = assertThrows(Exception::class.java){
            service.logIn("wrong@gmail.com","123456")
        }
        assertEquals("The mail is incorrect", exception.message)
    }
}