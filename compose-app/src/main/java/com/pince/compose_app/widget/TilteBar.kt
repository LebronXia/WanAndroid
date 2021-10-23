package com.pince.compose_app.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.pince.compose_app.LoginScreen
import com.pince.compose_app.R
import com.pince.compose_app.ui.theme.Colors
import com.pince.compose_app.ui.theme.WanAndroidTheme

/**
 * 标题栏
 * Created by zxb in 2021/10/20
 */
@Composable
fun TitleBar(
    title: String,
    onBack: (() -> Unit) ?= null,
    icon: Int ?= null,
    onIconClick: (() -> Unit) ?= null
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .background(Colors.titleBar)) {
        if (onBack != null){
            //align只能在Row中调用,用来设置子元素在垂直方向如何对齐
            Icon(modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .clickable {
                    onBack.invoke()
                }
                .size(48.dp)
                .padding(14.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "返回",
                tint = Colors.text_h1
                )
        }

        Text(text = title,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f),
            color = Colors.text_h1,
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
            )

        if (icon != null){
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        onIconClick?.invoke()
                    }
                    .size(48.dp)
                    .padding(14.dp),
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Colors.text_h1
                )
        }


    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanAndroidTheme {
        TitleBar("hahaha")

    }
}