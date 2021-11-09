package com.pince.compose_app.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

//带输入框的标题栏
@Composable
fun TitleBar(
    textFieldValue: TextFieldValue,
    onValueChange: (changeValue: TextFieldValue) -> Unit,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
){

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.height(54.dp),
    ) {

        Box(contentAlignment = Alignment.Center){

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    modifier = Modifier
                        .align(CenterVertically)
                        .clickable {
                            onLeftClick()
                        },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "返回"
                )

                Icon(imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onRightClick()
                        }
                        .padding(end = 10.dp),
                    tint = MaterialTheme.colors.background
                )
            }

            TextField(
                // 输入框内容
                value = textFieldValue,
                // 内容更改回调
                onValueChange = { changeValue ->
                    onValueChange(changeValue)
                },
                //单行
                singleLine = true,
                //输入框颜色
                colors = TextFieldDefaults.textFieldColors(
                    //文字
                    textColor = MaterialTheme.colors.background,
                    //背景
                    backgroundColor = MaterialTheme.colors.primary,
                    //光标
                    cursorColor = MaterialTheme.colors.background,
                    //当输入框处于/不处于焦点时，底部指示器的颜色
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary
                ),
                label = {
                    Text(text = "输入关键字", color = Color.Gray)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onRightClick()
                    }
                )
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