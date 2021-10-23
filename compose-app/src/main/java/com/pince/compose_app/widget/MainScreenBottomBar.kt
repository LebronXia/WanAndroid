package com.pince.compose_app.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pince.compose_app.model.MainScreenTab

/**
 * 底部栏
 * Created by zxb in 2021/10/22
 */
@Composable
fun MainScreenBottomBar(
    screenList: List<MainScreenTab>,
    screenSelected: MainScreenTab,
    onTabSelected: (MainScreenTab) -> Unit
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(color = MaterialTheme.colors.primaryVariant)
    ) {
        CommonDivider()
        Row{
            for (screen in screenList){
                val selected = screenSelected == screen
                BottomNavigationItem(
                    modifier = Modifier.weight(1f),
                    screen = screen,
                    tabSelected = selected,
                onClickTab = {
                    onTabSelected(screen)
                })
            }
        }
    }
}

//底部栏子布局
@Composable
private fun BottomNavigationItem(
    modifier: Modifier,
    screen: MainScreenTab,
    tabSelected: Boolean,
    onClickTab:() -> Unit
){

    val selectedContentColor = MaterialTheme.colors.surface
    val unselectContentColor = selectedContentColor.copy(alpha = 0.5f)
    val color = if (tabSelected) selectedContentColor else unselectContentColor

    Column(modifier = modifier.clickable {
        onClickTab()
    }) {

        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
            ){
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 2.dp)
                    .size(size = 28.dp)
                    .align(alignment = Alignment.TopCenter),
                    tint = color
            )
        }
        Text(
            text = screen.text,
            modifier = Modifier
                .fillMaxWidth()   //传统布局的match_parent
                .padding(bottom = 2.dp),
            color = color,
            style = MaterialTheme.typography.subtitle2,
            letterSpacing = 0.sp,
            textAlign = TextAlign.Center,
        )
    }
}