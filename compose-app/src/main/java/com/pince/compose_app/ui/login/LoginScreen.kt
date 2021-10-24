package com.pince.compose_app

import android.widget.TextView
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pince.compose_app.extend.navigateWithBack
import com.pince.compose_app.helper.UserInfoManager
import com.pince.compose_app.ui.login.LoginViewModel
import com.pince.compose_app.ui.theme.Colors
import com.pince.compose_app.ui.theme.Colors.text_h2
import com.pince.compose_app.ui.theme.WanAndroidTheme
import com.pince.compose_app.util.showToast
import com.pince.compose_app.widget.TitleBar
import com.xiamu.baselibs.util.toast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 登录页面
 */
@Composable
fun LoginScreen(navController: NavHostController) {  //navController: NavHostController
    WanAndroidTheme() {
        val viewModel: LoginViewModel = viewModel()
        val loginScreenState by viewModel.loginScreenState.collectAsState()
        var username by remember { mutableStateOf("") }
        var password by remember{ mutableStateOf("")}
        //让你在Composable中使用协程
        LaunchedEffect(Unit){
            launch {
                viewModel.loginScreenState.collect {
                    it.showSuccess?.let {
                        navController.navigateWithBack("main")
                    }
                    it.showError?.let {
                        showToast(it.toString())
                    }
                }
            }
            viewModel.autoLogin()
        }

        Box(modifier = Modifier.fillMaxSize()){
            Column(Modifier.fillMaxSize()) {

                TitleBar(title = "登录", onBack = {
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
                    Spacer(modifier = Modifier.height(50.dp))

                    Button(onClick = {viewModel.login(username, password)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(percent = 50)
                    ) {
                        Text(text = "登录", fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "没有账号？去注册",
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 16.dp)
                            .clickable {
                                navController.navigate("register")
                            },
                        color = Colors.text_h2,
                        fontSize = 15.sp
                    )
                }

            }

            if (loginScreenState.showLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .size(size = 60.dp)
                        .wrapContentSize(align = Alignment.Center),
                    strokeWidth = 4.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanAndroidTheme {
       LoginScreen(rememberNavController())
        //Text(text = "登录")
    }
}

