package com.pince.compose_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pince.compose_app.model.MainScreenTab
import com.pince.compose_app.ui.home.MainScreen
import com.pince.compose_app.ui.login.RegisterScreen
import com.pince.compose_app.ui.theme.WanAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationView()
        }
    }
}

@Composable
fun NavigationView(){
    val navController = rememberNavController()
    var screenSelected by remember {
        mutableStateOf(MainScreenTab.Home)
    }

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){LoginScreen(navController)}   //LoginScreen(navController)
        composable("register"){RegisterScreen(navController)}
        composable("main"){ MainScreen(navController = navController,
            screenSelected = screenSelected,
            onTabSelected = {
                screenSelected = it
            })}
    }
}
