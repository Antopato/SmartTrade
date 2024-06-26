package com.example.smarttrade

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smarttrade.databinding.LoginBinding
import com.example.smarttrade.ui.theme.SmartTradeTheme

class LogInActivity : AppCompatActivity() {

    lateinit var binding : LoginBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        val service = BusinessLogic();

        setContentView(binding.root)
        val errorText = binding.errorText


        binding.logIn.setOnClickListener{
            val userText = binding.username.text.toString()
            val passText = binding.password.text.toString()
            try {
                val user = service.logIn(userText, passText)
                if (user != null) {
                    println("El tipo es "+ user.type)
                }else{
                    println("por alguna razón es null")
                }
                var intent = Intent(this, CatalogActivity::class.java)
                intent.putExtra("user",user)

                startActivity(intent)
                errorText.visibility= View.INVISIBLE


            }catch(e:Exception){
                errorText.text=e.message
                errorText.visibility= View.VISIBLE
            }
        }

        binding.signIn.setOnClickListener() {
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