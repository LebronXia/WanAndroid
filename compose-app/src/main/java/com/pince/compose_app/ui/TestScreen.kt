package com.pince.compose_app.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pince.compose_app.R
import com.pince.compose_app.ui.theme.Typography
import com.pince.compose_app.ui.theme.WanAndroidTheme

/**
 * Created by zxb in 2021/10/24
 */
@Composable
fun Plain() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))   //边框
        .padding(8.dp)
    ) {
        Avatar(modifier = Modifier
            .padding(4.dp)
            .align(Alignment.CenterVertically)
        )

        Info( //文字部分
            Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )

        FollowBtn(Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun Avatar(modifier: Modifier){
    Image(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .border(
                shape = CircleShape,
                border = BorderStroke(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Blue, Color.Red, Color.Green),
                        start = Offset(0f, 0f),
                        end = Offset(100f, 100f)
                    )
                )
            )  //颜色边框
            .border(
                shape = CircleShape,
                border = BorderStroke(4.dp, SolidColor(Color.White))
            ),  //白色边框
        painter = painterResource(id = R.drawable.oo),
        contentDescription = null)
}

@Composable
fun Info(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "name",
            color = Color.Black,
            maxLines = 1,
            style = TextStyle(
                fontWeight = FontWeight.Bold,  //字体类型
                fontSize = 16.sp,
                letterSpacing = 0.15.sp  //字符之间的空白
            )
        )
        Text(
            text = "desc",
            color = Color.Black.copy(alpha = 0.75f),
            maxLines = 1,
            style = TextStyle( // here
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.25.sp
            )
        )
    }
}

@Composable
fun FollowBtn(modifier: Modifier){
    val backgroundShape: Shape = RoundedCornerShape(4.dp)

    Text(text = "Follow",
        style = Typography.body1.copy(color = Color.White),
        textAlign = TextAlign.Center,
        modifier = modifier
            .width(80.dp)
            .clickable(onClick = {})
            .shadow(3.dp, shape = backgroundShape)  //阴影
            .clip(backgroundShape)
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color.Red, Color.Green),
                startY = 0f,
                endY = 80f
            ))
            .padding(6.dp)
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanAndroidTheme {
        Plain()
    }
}