package com.santosh.navigate_sample
import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.santosh.navigate_sample.ui.theme.Navigate_sampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigate_sampleTheme {
                    NavigationApp()
            }
        }
    }
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
                //HomeScreenWithBasic(navController)
        }
 /*       composable(Screen.Detail.route, arguments = listOf(
            navArgument("name") { type= NavType.StringType },
            navArgument("password") { type = NavType.StringType }
        )) {
            backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val pass = backStackEntry.arguments?.getString("password") ?: ""
            DetailScreenWithBasic(name,pass)
        }*/
        composable(route = Screen.Detail.route, arguments = listOf(navArgument("userId") {
            type= NavType.StringType  })
        ) {
            backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            val viewModel: DataViewModel = viewModel()
            LaunchedEffect(Unit) {
                viewModel.loadUser(userId)
            }
            DetailScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var selectedUserId by remember { mutableStateOf<Int?>(null) }
    var showError by remember {mutableStateOf(false)}
    val usersList = UserRepository.users
    Scaffold(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
            Card(shape = RoundedCornerShape(12.dp) , elevation = CardDefaults.cardElevation(8.dp), colors = CardDefaults.elevatedCardColors()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Sample Safe Navigation Demo")
                    Spacer(modifier = Modifier.height(6.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {expanded = !expanded}
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.menuAnchor(),
                            value = selectedUserId?.toString() ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = {Text("Select User ID")},
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                            }
                        )
                        ExposedDropdownMenu(
                            expanded=expanded,
                            onDismissRequest = {expanded=false}
                        ) {
                            usersList.forEach {
                                user ->
                                DropdownMenuItem(
                                    text = {Text("User ${user.id}")},
                                    onClick = {
                                        selectedUserId=user.id
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        enabled = selectedUserId != null,
                        onClick = {
                            navController.navigate()
                        }
                    ) {

                    }

                }
            }
        }

    }

}

@Composable
fun DetailScreen(viewModel: DataViewModel) {
    val user = viewModel.user
    Scaffold(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing))
    { innerPadding ->
        Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
            Card (shape = RoundedCornerShape(24.dp), elevation = CardDefaults.cardElevation(8.dp)){
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("RECEIVED VALUES" , modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Hi, ${user?.name}", modifier = Modifier.padding(12.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Your Password is ${user?.password}", modifier = Modifier.padding(12.dp))
                }
            }


        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreenWithBasic(name:String, password:String) {
    Scaffold(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing))
    {
        innerPadding ->
        Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
            Card (shape = RoundedCornerShape(24.dp), elevation = CardDefaults.cardElevation(8.dp)){
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("RECEIVED VALUES" , modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Hi, $name", modifier = Modifier.padding(12.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Your Password is $password", modifier = Modifier.padding(12.dp))
                }
            }


        }

    }
}

@Composable
fun HomeScreenWithBasic(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var showNameWarning by remember { mutableStateOf(false) }
    var showPassWarning by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Card(shape = RoundedCornerShape(24.dp), elevation = CardDefaults.cardElevation(8.dp), colors = CardDefaults.elevatedCardColors() ) {
            Column (modifier = Modifier.padding(12.dp)){
                Text(text = "Form Details", modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally), style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value = firstName,
                    onValueChange = {firstName=it
                        if(it.isNotEmpty()) showNameWarning=false },
                    label = {Text("Enter First Name")},
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    isError = showNameWarning
                )
                if(showNameWarning) {
                    Text(
                        text = "Name cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp,top=4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value =lastName,
                    onValueChange = {lastName=it},
                    label = {Text("Enter password")} ,
                    leadingIcon = {Icon(Icons.Default.Password ,
                        contentDescription = null)} ,
                    isError = showPassWarning
                )
                if(showPassWarning){
                    Text(
                        text = "Password cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        showNameWarning = firstName.isBlank()
                        showPassWarning = lastName.isBlank()
                        if(!showNameWarning && !showPassWarning) {

                        }
                    },
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
        //Greeting()
        val navController = rememberNavController()
       // HomeScreenWithBasic(navController)
       // DetailScreenWithBasic("","")
        HomeScreen(navController)
    }
}

sealed class Screen(val route: String) {
    data object Home: Screen("home")
/*    data object Detail: Screen("detail/{name}/{password}") {
        fun sendDetails(name: String, password: String):String {
            return  "detail/$name/$password"
        }
    }*/
    data object Detail : Screen("detail/{userId}") {
        fun createRoute(userId:Int):String {
            return "detail/$userId"
        }
    }
}