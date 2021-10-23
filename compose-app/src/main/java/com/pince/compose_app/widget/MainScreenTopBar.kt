package com.pince.compose_app.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.statusBarsPadding
import com.pince.compose_app.model.MainScreenTab
import com.pince.compose_app.ui.theme.Colors

/**
 * 主页顶部标题栏
 * Created by zxb in 2021/10/23
 */
@Composable
fun MainScreenTopBar(
    screenSelected: MainScreenTab,
    openDrawer: () -> Unit,
    onIconClick: (() -> Unit)? = null
) {

    val topBarPadding = 18.dp
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.primaryVariant)
        .statusBarsPadding()
        .padding(top = topBarPadding)
    ) {
        val (drawerIcon, titleText, searchIcon, divider) = createRefs()

        Icon(modifier = Modifier
            .constrainAs(drawerIcon) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
            .padding(start = 6.dp)
            .size(size = 28.dp)
            .clickable(onClick = openDrawer),
            imageVector = Icons.Filled.Menu,
            contentDescription = null,
            tint = MaterialTheme.colors.surface
        )

        Text(text = screenSelected.text,
            modifier = Modifier
                .constrainAs(titleText) {
                    start.linkTo(anchor = drawerIcon.end)
                    top.linkTo(anchor = drawerIcon.top)
                    bottom.linkTo(anchor = drawerIcon.bottom)
                }
                .padding(start = 12.dp),
            color = Colors.text_h1,
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            modifier = Modifier
                .constrainAs(searchIcon) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(end = 12.dp)
                .size(28.dp)
                .clickable {
                    onIconClick?.invoke()
                },
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colors.surface)

        CommonDivider(modifier = Modifier
            .constrainAs(divider){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(anchor = titleText.bottom, margin = topBarPadding)
            })
    }

}