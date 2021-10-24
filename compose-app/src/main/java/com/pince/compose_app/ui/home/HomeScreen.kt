package com.pince.compose_app.ui.home

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.pince.compose_app.LoginScreen
import com.pince.compose_app.R
import com.pince.compose_app.model.entry.Article
import com.pince.compose_app.ui.home.viewmodel.HomeViewModel
import com.pince.compose_app.ui.theme.Colors
import com.pince.compose_app.ui.theme.Typography
import com.pince.compose_app.ui.theme.WanAndroidTheme
import com.pince.compose_app.util.showToast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Created by zxb in 2021/10/23
 */
@Composable
fun HomeScreen(paddingValues: PaddingValues){

    WanAndroidTheme {

        val viewModel: HomeViewModel = viewModel()
        val homeScreenstate by viewModel.homeScreenState.collectAsState()
        var list by remember { mutableStateOf(listOf<Any>()) }
        LaunchedEffect(Unit){
            launch {
                viewModel.homeScreenState.collect{
                    it.showSuccess?.let {
                        list = it
                    }
                    it.showError?.let {
                        showToast(it.toString())
                    }
                }
            }
        }

        //从组件树移除时
        DisposableEffect(key1 = Unit ){
            onDispose {

            }
        }

        Scaffold(modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .fillMaxSize()
        ) {

            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(Colors.white)
            ){
                itemsIndexed(list){ index, item ->
                    if (item is List<*>){

                    } else if (item is Article){
                        ArticleItem(
                            modifier = Modifier,
                            article = item,
                            onClickItemClick = {

                            },
                            onCollectClick = {

                            }
                        )

                        Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}


