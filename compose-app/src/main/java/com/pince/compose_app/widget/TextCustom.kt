package com.pince.compose_app.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by zxb in 2021/11/8
 */
@Composable
fun RowTextIcon(
    text: String,
    paddingTop: Dp = 0.dp,
    imageVector: ImageVector = Icons.Default.Close,
    imageClick: (CoroutineScope.() -> Unit) ?= null
){
    var index by remember{ mutableStateOf(0) }

    //LaunchedEffect 监听每次index改变都会触发
    LaunchedEffect(index){

        if (index == 0) return@LaunchedEffect

        launch(Dispatchers.IO) {
            if (imageClick != null) imageClick()
        }
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = paddingTop)
        .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween){

        Text(text, fontSize = 16.sp, color = Color.Gray)

        Icon(imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.clickable(
                onClick = {
                    imageClick?.let { index ++ }
                }
            ).padding(5.dp),
            tint = Color.Gray
        )
    }
}