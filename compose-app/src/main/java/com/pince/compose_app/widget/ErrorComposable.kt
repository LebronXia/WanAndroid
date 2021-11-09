package com.pince.compose_app.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pince.compose_app.R

/**
 * Created by zxb in 2021/11/4
 */
@Composable
fun ErrorComposable(title: String = "网络不佳，请点击重试", block:() -> Unit){
    
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        Image(
            modifier = Modifier.size(300.dp, 180.dp),
            painter = painterResource(id = R.drawable.img_tips_error_load_error), 
            contentDescription = "网络问题",
            contentScale = ContentScale.Crop
            )
        
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = block) {
            Text(text = title)
        }

    }
}