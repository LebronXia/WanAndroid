package com.pince.compose_app.ui.public

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.pince.compose_app.ui.theme.WanAndroidTheme

/**
 * 微信公众号
 * Created by zxb in 2021/11/8
 */
@ExperimentalPagerApi   //试验性的特性
@Composable
fun PublicWechatScreen(paddingValues: PaddingValues) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(

    )

    WanAndroidTheme {


        Column() {

//            ScrollableTabRow(
//                selectedTabIndex = ) {
//
//            }
        }
    }
}