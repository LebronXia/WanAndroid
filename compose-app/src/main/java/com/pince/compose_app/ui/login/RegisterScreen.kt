package com.pince.compose_app.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pince.compose_app.ui.theme.Colors
import com.pince.compose_app.ui.theme.WanAndroidTheme
import com.pince.compose_app.widget.TitleBar

/**
 * Created by zxb in 2021/10/20
 */
@Composable
fun RegisterScreen(navController: NavHostController) {
    val viewModel: LoginViewModel = viewModel()

    WanAndroidTheme() {

        var username by remember { mutableStateOf("") }
        var password by remember{ mutableStateOf("") }
        var repassword by remember{ mutableStateOf("")}

        Column(Modifier.fillMaxSize()) {
            TitleBar(title = "注册", onBack = {
                navController.popBackStack()
            })

            Column(Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(120.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = {value ->
                        username = value  //值更改回调
                    },
                    modifier = Modifier.fillMaxWidth(), //修饰  宽度
                    label = {
                        Text(text = "请输入用户名")   //默认显示
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), //字符类型
                    singleLine = true,  //单行
                    colors = TextFieldDefaults.outlinedTextFieldColors(   //集中的颜色
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)//厚度
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {value ->
                        password = value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "请输入密码")   //默认显示
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = repassword,
                    onValueChange = {value ->
                        repassword = value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "请确认密码")   //默认显示
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(50.dp))

                Button(onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(percent = 50)
                ) {
                    Text(text = "登录", fontSize = 15.sp)
                }
            }

        }
    }

}