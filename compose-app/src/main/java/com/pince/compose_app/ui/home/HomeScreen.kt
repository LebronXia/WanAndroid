package com.pince.compose_app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.pince.compose_app.LoginScreen
import com.pince.compose_app.ui.theme.WanAndroidTheme
import kotlin.random.Random

/**
 * Created by zxb in 2021/10/23
 */
@Composable
fun HomeScreen(paddingValues: PaddingValues){

    Scaffold(modifier = Modifier
        .padding(bottom = paddingValues.calculateBottomPadding())
        .fillMaxSize()

    ) {
        Text("Hello World${(0 .. 10).random()}", textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center),
            fontSize = 30.sp
        )
    }
}

@Composable
fun ItalicText() {

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanAndroidTheme {

    }
}
