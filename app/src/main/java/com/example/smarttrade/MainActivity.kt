package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smarttrade.ui.theme.SmartTradeTheme
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login)
        val logInButt = findViewById<Button>(R.id.logIn)
        val signIn = findViewById<TextView>(R.id.signIn)

        logInButt.setOnClickListener{
            val userText = findViewById<EditText>(R.id.username)
            val passText = findViewById<EditText>(R.id.password)
            logIn(userText.text.toString(), passText.text.toString())
            println(userText.text.toString())
        }

        signIn.setOnClickListener() {
            println("Has pulsado register")
            startActivity(Intent(this, CostumerRegisterActivity::class.java))
        }


        /*setContent {
            SmartTradeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }*/
    }
    fun logIn(name: String, pass:String){
        /*if(checkLog()){
            //move to main page
        }*/

    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        //Pruebagit
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartTradeTheme {
        Greeting("Android")
    }
}