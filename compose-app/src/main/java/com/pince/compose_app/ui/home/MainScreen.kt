package com.pince.compose_app.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.pince.compose_app.model.MainScreenTab
import com.pince.compose_app.model.storage.AppPreferences
import com.pince.compose_app.ui.theme.WanAndroidTheme
import com.pince.compose_app.util.showToast
import com.pince.compose_app.widget.MainScreenBottomBar
import com.pince.compose_app.widget.MainScreenTopBar
import com.xiamu.wanandroid.mvvm.model.entry.LoginBean
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 主页面
 * Created by zxb in 2021/10/22
 */
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen(navController: NavController,
               screenSelected: MainScreenTab,
               onTabSelected: (MainScreenTab) -> Unit){

    WanAndroidTheme() {
        //读取到系统状态栏的高度了
        ProvideWindowInsets{
            //设置抽屉的打开状态
            var scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
            //类似于GlobalScope一样，这个就是让你在Composable中使用协程的
            var coroutineScope = rememberCoroutineScope()
            var user : LoginBean ?= null
            LaunchedEffect(Unit){
                launch {
                     user = AppPreferences.getUser()
                }

            }
            //带整体的布局
            Scaffold(
                //开关
                scaffoldState = scaffoldState,
                //顶部栏
                topBar = {
                    MainScreenTopBar(
                        screenSelected = screenSelected,
                        openDrawer = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                        onIconClick = {
                           // showToast("点击搜索~~~~")
                            navController.navigate("search")
                        }
                    )
                },
                floatingActionButton = {
//                    FloatingActionButton(onClick = {
//                        user?.let {
//                            showToast("点击了~~~~${user!!.nickname}")
//                        }
//
//
//                    }) {
//                        Text(text = "x")
//                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                //抽屉内容
                drawerContent = { Text(text = "drawerContent")},
                bottomBar = {
                    MainScreenBottomBar(
                        screenList = MainScreenTab.values().toList(),
                        screenSelected = screenSelected, 
                        onTabSelected = onTabSelected
                    )
                }
            ) {
                paddingValues ->
                    when(screenSelected){
                        MainScreenTab.Home -> {
                            HomeScreen(paddingValues = paddingValues)
                        }
                        MainScreenTab.KnowledgeSystem -> {
                            HomeScreen(paddingValues = paddingValues)
                        }
                        MainScreenTab.Wechat -> {
                            HomeScreen(paddingValues = paddingValues)
                        }
                        MainScreenTab.Navigation -> {
                            HomeScreen(paddingValues = paddingValues)
                        }
                        MainScreenTab.Project -> {
                            HomeScreen(paddingValues = paddingValues)
                        }
                    }
            }
        }
    }
}

