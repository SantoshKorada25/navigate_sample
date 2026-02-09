package com.santosh.navigate_sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santosh.navigate_sample.ui.theme.Navigate_sampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigate_sampleTheme {
                    Greeting(

                    )

            }
        }
    }
}

@Composable
fun Greeting() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
                HomeScreen(navController)
        }
        composable(Screen.Detail.route) {
            DetailScreen()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen() {
    Scaffold(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
        innerPadding ->
        Text("Welcome to Details Screen " , modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Card(shape = RoundedCornerShape(24.dp), elevation = CardDefaults.cardElevation(8.dp), colors = CardDefaults.elevatedCardColors() ) {
            Column (modifier = Modifier.padding(12.dp)){
                Text(text = "Form Details", modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally), style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value = firstName, onValueChange = {firstName=it}, label = {Text("Enter First Name")},leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) })
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value =lastName, onValueChange = {lastName=it}, label = {Text("Enter First Name")} , leadingIcon = {Icon(Icons.Default.Password , contentDescription = null)} )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        navController.navigate(Screen.Detail.createRoute("Santosh","123456"))
                    }
                ) {
                    Text("Submit")
                }

            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Navigate_sampleTheme {
        Greeting()
    }
}

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object Detail: Screen("detail/{name}/{password}") {
        fun createRoute(name: String, password: String):String {
            return  "detail/$name/$password"
        }
    }
}