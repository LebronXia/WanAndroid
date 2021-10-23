package com.pince.compose_app.widget

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by zxb in 2021/10/22
 */
@Composable
fun CommonDivider(modifier: Modifier = Modifier){
    Divider(
        modifier = modifier, thickness = 0.3.dp
    )
}